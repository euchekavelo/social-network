package ru.skillbox.socnetwork.service.storage;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.oauth.DbxRefreshResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class StorageTokenUpdate {

  private static DbxCredential credential;
  private static final String APP_ID = "272m6nu2hx6xsuw";
  private static final String PATH = "/tmp/credentials.json";

    @Scheduled(fixedRateString = "PT03H")
  public void refreshToken() throws JsonReader.FileLoadException, DbxException, IOException {
    File savedCredentials = new File(PATH);
    if(!savedCredentials.exists()) {
      //Read app info file
      DbxAppInfo appInfo;
      DbxAuthFinish authFinish;
      appInfo = new DbxAppInfo("272m6nu2hx6xsuw", "ch18f6015h71dqy");

      //Run authorization process
      authFinish = new ScopeAuthorize().authorize(appInfo);

      credential = new DbxCredential(authFinish.getAccessToken(), authFinish
              .getExpiresAt(), authFinish.getRefreshToken(), appInfo.getKey(), appInfo.getSecret());

      DbxCredential.Writer.writeToFile(credential, savedCredentials);
    }

    credential = DbxCredential.Reader.readFromFile(savedCredentials);

    //Refresh token
    DbxRequestConfig config = new DbxRequestConfig(APP_ID);
    DbxRefreshResult result = credential.refresh(config);

    //Save new credentials
    DbxCredential newCredential = new DbxCredential(
              result.getAccessToken(), result.getExpiresAt(),
              credential.getRefreshToken(), credential.getAppKey(), credential.getAppSecret());
    DbxCredential.Writer.writeToFile(newCredential, new File(PATH));

    StorageService.updateToken(result.getAccessToken());
  }
}
