package ru.skillbox.socnetwork.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StorageService {
  private static String token = "sl.BFoJTj8a6ZrrlnoaC8cWn2E1cjw281ptAJaAR1fP8nTP1PIQws0LaiNvNI17vkxoFLVn2bqGCEcp-XNhOJIT3nGUoZ3UK2CYTDpiakmNUWPUKWFUVyDtTze1vl4-LG6_YBA2dWl8";
  private static final DbxRequestConfig config = DbxRequestConfig.newBuilder("socNet").build();
  private static DbxClientV2 client = new DbxClientV2(config, token);

  public String getDefaultProfileImage() throws DbxException {
    return client.sharing().listSharedLinksBuilder().withPath("/default.jpg").start()
            .getLinks().get(0).getUrl().replace("dl=0", "raw=1");
  }

  public FileMetadata uploadFile(InputStream stream, String path) {
    FileMetadata fileMetadata = null;
    try {
      fileMetadata = client.files().uploadBuilder("/" + path)
              .withMode(WriteMode.ADD).uploadAndFinish(stream);
      if (client.sharing().listSharedLinksBuilder().withPath("/" + path).start()
              .getLinks().isEmpty()) {
        client.sharing().createSharedLinkWithSettings(fileMetadata.getPathDisplay());
      }
    } catch (DbxException | IOException e) {
      e.printStackTrace();
    }
    return fileMetadata;
  }

  public void deleteFile(String path) throws DbxException {
    if (!path.equals("/default.jpg")) {
      client.files().deleteV2(path);
    }
  }

  public void updateToken(String newToken) {
    token = newToken;
    client = new DbxClientV2(config, token);
  }

  public String getRelativePath(String path) {
    Pattern pattern = Pattern.compile(".*(/\\w*\\.[A-z]*)\\?raw=1");
    Matcher matcher = pattern.matcher(path);
    return (matcher.find()) ? matcher.group(1) : "";
  }

  public String getAbsolutePath(String path) throws DbxException {
    return client.sharing().listSharedLinksBuilder().withPath(path).start()
            .getLinks().get(0).getUrl().replace("dl=0", "raw=1");
  }
}
