package ru.skillbox.socnetwork.service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedsService {

  private PersonService personService;

  private static int id = 1;
  private final String title = "Some title string";
  private final String paragraph = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
      + "Suspendisse blandit volutpat sem non cursus. Nam.";
  private final Map<String, Object> COMMENTS = Map.ofEntries(
      new AbstractMap.SimpleEntry<>("parent_id", 1),
      new AbstractMap.SimpleEntry<>("comment_text", "Lorem ipsum dolor sit amet, consectetur."),
      new AbstractMap.SimpleEntry<>("id", 1),
      new AbstractMap.SimpleEntry<>("post_id", 1),
      new AbstractMap.SimpleEntry<>("time", System.currentTimeMillis() - 7_000_000),
      new AbstractMap.SimpleEntry<>("author_id", 1),
      new AbstractMap.SimpleEntry<>("is_blocked", false)
  );
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
      new AbstractMap.SimpleEntry<>("city", "Arkham"),
      new AbstractMap.SimpleEntry<>("country", "USA"),
      new AbstractMap.SimpleEntry<>("messages_permission", "ALL"),
      new AbstractMap.SimpleEntry<>("last_online_time", 1559751301818L),
      new AbstractMap.SimpleEntry<>("is_blocked", false)
  );

//      "ivanov@mail.ru"
//      "petrov@mail.ru"
//      "tihonov@mail.ru"
//      "ilin@mail.ru"
//      "onufriy@mail.ru"


  private Map<String, Object> getPost() { //TODO: получать список постов с репы
    Map<String, Object> post = Map.ofEntries
      (
      new AbstractMap.SimpleEntry<>("id",id++),
      new AbstractMap.SimpleEntry<>("time",System.currentTimeMillis() - 7_200_000),
      new AbstractMap.SimpleEntry<>("author", USER),
      new AbstractMap.SimpleEntry<>("title", title + " " + id),
      new AbstractMap.SimpleEntry<>("post_text", paragraph),
      new AbstractMap.SimpleEntry<>("is_blocked",false),
      new AbstractMap.SimpleEntry<>("likes", new Random().nextInt(666)),
      new AbstractMap.SimpleEntry<>("comments",List.of(COMMENTS))
      );
    return post;
  }

  public Map<String, Object> getFeeds(){ //TODO: Получение и валидация токена
    return Map
        .of("error", "string",
            "timestamp", System.currentTimeMillis(),
            "total", 0,
            "offset", 0,
            "perPage", 20,
            "data", List.of(getPost())
        );
  }
}
