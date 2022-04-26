package ru.skillbox.socnetwork.service.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageCache {
  private static final Map<String, String> cache = new HashMap<>();

  public String addLink(String fileName, String link){
    cache.put(fileName, link);
    return cache.get(fileName);
  }

  public String getLink(String fileName){
    return cache.get(fileName);
  }

  public void deleteLink(String fileName){
    cache.remove(fileName);
  }
}
