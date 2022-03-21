package ru.skillbox.socnetwork.model.rsdto;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class TempResponseDto {

  private static final Map<String, Object> PLACE = Map
      .of("id", 1,
          "title", "city/country");

  private static final Map<String, Object> USER = Map.ofEntries(
      new AbstractMap.SimpleEntry<>("id", 1),
      new AbstractMap.SimpleEntry<>("first_name", "Petr"),
      new AbstractMap.SimpleEntry<>("last_name", "Петрович"),
      new AbstractMap.SimpleEntry<>("reg_date", 1559751301818L),
      new AbstractMap.SimpleEntry<>("birth_date", 1559751301818L),
      new AbstractMap.SimpleEntry<>("email", "petr@mail.ru"),
      new AbstractMap.SimpleEntry<>("phone", "89100000000"),
      new AbstractMap.SimpleEntry<>("photo", "https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg"),
      new AbstractMap.SimpleEntry<>("about", "Родился"),
      //TODO найдена ошибка на свагере, у city и country ключ не мапа, а строка
      new AbstractMap.SimpleEntry<>("city", "Arkham"),
      new AbstractMap.SimpleEntry<>("country", "USA"),
      new AbstractMap.SimpleEntry<>("messages_permission", "ALL"),
      new AbstractMap.SimpleEntry<>("last_online_time", 1559751301818L),
      new AbstractMap.SimpleEntry<>("is_blocked", false)
  );

  private static final Map<String, Object> COMMENTS = Map.ofEntries(
      new AbstractMap.SimpleEntry<>("parent_id", 1),
      new AbstractMap.SimpleEntry<>("comment_text", "string"),
      new AbstractMap.SimpleEntry<>("id", 1),
      new AbstractMap.SimpleEntry<>("post_id", 1),
      new AbstractMap.SimpleEntry<>("time", 1559751301818L),
      new AbstractMap.SimpleEntry<>("author_id", 1),
      new AbstractMap.SimpleEntry<>("is_blocked", true)
  );

  public static final Map<String, Object> PROFILE_RESPONSE = Map
      .of("error", "test error",
          "timestamp", 1559751301818L,
          "data", USER
      );

  public static final Map<String, Object> COMMENTS_TO_POST_RESPONSE = Map
      .of("error", "string",
          "timestamp", 1559751301818L,
          "total", 0,
          "offset", 0,
          "perPage", 20,
          "data", List.of(COMMENTS)
      );

  private static final Map<String, Object> FEED_DATA_MAP = Map.ofEntries
      (
          new AbstractMap.SimpleEntry<>("id", 1),
          new AbstractMap.SimpleEntry<>("time", 1559751301818L),
          new AbstractMap.SimpleEntry<>("author", USER),
          new AbstractMap.SimpleEntry<>("title", "string"),
          new AbstractMap.SimpleEntry<>("post_text", "string"),
          new AbstractMap.SimpleEntry<>("is_blocked", false),
          new AbstractMap.SimpleEntry<>("likes", 201),
          new AbstractMap.SimpleEntry<>("comments", List.of(COMMENTS))
      );

  public static final Map<String, Object> POSTS_FEEDS_RESPONSE = Map
      .of("error", "string",
          "timestamp", 1559751301818L,
          "total", 0,
          "offset", 0,
          "perPage", 20,
          "data", List.of(FEED_DATA_MAP)
      );

  public static final Map<String, Object> FRIENDS_RESPONSE = Map
      .of("error", "string",
          "timestamp", 1559751301818L,
          "total", 0,
          "offset", 0,
          "perPage", 20,
          "data", List.of(USER)
      );

  public static final Map<String, Object> TAG_RESPONSE = Map
      .of("error", "string",
          "timestamp", 1559751301818L,
          "total", 0,
          "offset", 0,
          "perPage", 20,
          "data", List.of(
              new AbstractMap.SimpleEntry<>("id", 1),
              new AbstractMap.SimpleEntry<>("tag", "tag_name")
          )
      );

  public static final Map<String, Object> NOTIFICATION_RESPONSE = Map
      .of("error", "string",
          "timestamp", 1559751301818L,
          "total", 0,
          "offset", 0,
          "perPage", 20,
          "data", List.of(
              new AbstractMap.SimpleEntry<>("id", 1),
              new AbstractMap.SimpleEntry<>("type_id", 1),
              new AbstractMap.SimpleEntry<>("sent_time", 1559751301818L),
              new AbstractMap.SimpleEntry<>("entity_id", 1),
              new AbstractMap.SimpleEntry<>("info", "info_text")
          )
      );
}
