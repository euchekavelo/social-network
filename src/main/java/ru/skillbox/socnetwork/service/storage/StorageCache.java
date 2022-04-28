package ru.skillbox.socnetwork.service.storage;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StorageCache {
  public static final String DEFAULT = "/default.jpg";
  private static final String DEFAULT_LINK = "https://www.dropbox.com/s/ekczqxzi1jw8b0y/default.jpg?raw=1";
  public static final String DELETED = "/deleted.jpg";
  private static final String DELETED_LINK = "https://www.dropbox.com/s/3l8tr9rii4sq30y/deleted.jpg?raw=1";

  private static final Map<String, String> cache = Map.of(
          DEFAULT, DEFAULT_LINK,
          DELETED, DELETED_LINK
  );

  public String addLink(String fileName, String link){
    cache.put(fileName, link);
    return cache.get(fileName);
  }

  public String getLink(String fileName){
    return cache.get(fileName);
  }

  public Boolean isLinkExists(String fileName){
    return cache.containsKey(fileName);
  }

  public void deleteLink(String fileName){
    cache.remove(fileName);
  }
}
