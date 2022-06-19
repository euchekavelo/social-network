package ru.skillbox.socnetwork.service.storage;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.oauth.DbxRefreshResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class StorageTokenUpdate {

  private static DbxCredential credential;
  private static final String APP_ID = "272m6nu2hx6xsuw";
  private static final String PATH = "/tmp/credentials.json";

  public StorageTokenUpdate() throws JsonReader.FileLoadException {
    credential = DbxCredential.Reader.readFromFile(new File(PATH));
  }

  @Scheduled(fixedRateString = "PT03H", initialDelayString = "PT3M")
  public void refreshToken() throws DbxException {

    DbxRequestConfig config = new DbxRequestConfig(APP_ID);
    DbxRefreshResult result = credential.refresh(config);

    credential = new DbxCredential(result.getAccessToken(), result.getExpiresAt(), credential.getRefreshToken(),
            credential.getAppKey(), credential.getAppSecret());

    StorageService.updateToken(result.getAccessToken());
  }
}
