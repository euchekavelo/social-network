package ru.skillbox.socnetwork.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.stereotype.Service;

@Service
public class PhotoStorageService {
  private static final String TOKEN = "sl.BFdMJLH6c3cUcb2mPmuCze6iV2mJP_1BhdwEaNq5UJmJk7coqO3LO5B39toeMxdp5g_bumkQKkeGUt_TpXUqHK1g4BOfdpAxDsasuCKbUaNAvF5zMU9qhJOQ0bmLjLIoMZSzdPFJ";
  private static final DbxRequestConfig config = DbxRequestConfig.newBuilder("socNet").build();
  private static final DbxClientV2 client = new DbxClientV2(config, TOKEN);

  public String getDefaultProfileImage() throws DbxException {
    return client.sharing().listSharedLinksBuilder().withPath("/default.jpg").start()
        .getLinks().get(0).getUrl().replace("dl=0","raw=1");
  }
}
