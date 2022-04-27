package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.Post;

import java.util.*;

@Service
@RequiredArgsConstructor
@DebugLogs
public class FeedsService {

  private static int id = 1;
  private final PersonService personService;
  private final PostService postService;
  private final Map<String, Object> COMMENTS = Map.ofEntries(
          //Временно закоменчено, пока не прояснится фронт
//          new AbstractMap.SimpleEntry<>("parent_id", 16),
          new AbstractMap.SimpleEntry<>("comment_text", "Nice post!"),
          new AbstractMap.SimpleEntry<>("id", 16),
          new AbstractMap.SimpleEntry<>("post_id", 1),
          new AbstractMap.SimpleEntry<>("time", System.currentTimeMillis() - 7_000_000),
          new AbstractMap.SimpleEntry<>("author_id", new Random().nextInt(6) + 1),
          new AbstractMap.SimpleEntry<>("is_blocked", false)
  );

  private Map<String, Object> getPost() {
    Person person = personService.getById(new Random().nextInt(6) + 1);
    person.setPhoto("https://st2.depositphotos.com/1001599/7010/v/600/depositphotos_70104863-stock-illustration-man-holding-book-under-his.jpg");
    List<Map<String, Object>> list;
    if (new Random().nextBoolean()) {
      list = Collections.singletonList(COMMENTS);
    } else {
      list = Collections.EMPTY_LIST;
    }
    Map<String, Object> post = Map.ofEntries
            (
                    new AbstractMap.SimpleEntry<>("id", id++),
                    new AbstractMap.SimpleEntry<>("time", System.currentTimeMillis() - (Math.random() * 35_000_000) + 1_000_000),
                    new AbstractMap.SimpleEntry<>("author", person),
                    new AbstractMap.SimpleEntry<>("title", getTitle()),
                    new AbstractMap.SimpleEntry<>("post_text", getLoremIpsum(new Random().nextInt(700) + 300)),
                    new AbstractMap.SimpleEntry<>("is_blocked", false),
                    new AbstractMap.SimpleEntry<>("likes", new Random().nextInt(666)),
                    new AbstractMap.SimpleEntry<>("comments", list)
            );
    return post;
  }

  public Map<String, Object> getFeeds() {
    List<Map<String, Object>> posts = new ArrayList<>();
    for (int i = 0; i < new Random().nextInt(4) + 1; i++) {
      posts.add(getPost());
    }
//    List<Post> posts = postService.getAll();
    return Map
            .of("error", "string",
                    "timestamp", System.currentTimeMillis(),
                    "total", posts.size(),
                    "offset", 0,
                    "perPage", 20,
                    "data", posts
            );
  }

  private String getLoremIpsum(int limit) {
    String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed egestas porttitor leo, eget " +
            "efficitur orci. Sed aliquam, neque non pharetra tristique, nulla orci vulputate ex, sed accumsan " +
            "ipsum erat vulputate lorem. Fusce sit amet lobortis nulla. Ut efficitur sollicitudin dui, eu imperdiet " +
            "mauris vehicula ac. Ut in enim quis eros finibus dignissim feugiat eu nisl. Nulla magna orci, rhoncus " +
            "eu aliquet non, suscipit et urna. Proin efficitur est ac nisl vulputate, at efficitur justo viverra. " +
            "Quisque egestas in lacus nec tincidunt. Quisque aliquet at ante in suscipit. Nullam est nulla, " +
            "elementum quis lorem eget, pulvinar imperdiet lacus. Cras elementum felis eros, non tincidunt mauris " +
            "dictum id. Phasellus rhoncus sagittis malesuada. Integer ultricies blandit felis, nec elementum urna " +
            "tristique quis. Morbi euismod vitae lacus non condimentum. Nunc varius orci ex, vel blandit eros " +
            "auctor sed. Fusce porttitor a eros vitae elementum. Sed et massa ipsum. Nulla nunc felis, pellentesque " +
            "id rutrum ut, pulvinar vel orci. Morbi a sodales dui. Maecenas at mauris elit. Duis et est luctus, " +
            "auctor nisi eu, iaculis leo. Nunc auctor risus suscipit lectus maximus tincidunt. Class aptent taciti " +
            "sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis eget feugiat leo, laoreet " +
            "ultrices tellus. Phasellus sed.";
    return (limit >= lorem.length()) ? lorem : lorem.substring(0, limit);
  }

  private String getTitle() {
    List<String> titles = List.of(
            "Вы в своем уме, или как преодолеть программирование без стрессов",
            "Как изменить программирование , не изменяя при этом себе",
            "программирование будущего: прочитайте и запомните сегодня, чтобы не пожалеть об этом",
            "10 фактов о программировании , которые не дадут вам заснуть",
            "Странные и поразительные факты о программировании",
            "программирование — табу, которое вам следует сломать",
            "7 бед, но программирование — ответ",
            "Почему мы совершаем одинаковые ошибки в программировании",
            "Что? Где? Почем? Изучаем программирование",
            "Программирование в конце квартала считают",
            "Кабы не было беды, а все время программирование",
            "Убейте свои сомнения раз и навсегда: используйте программирование",
            "Думай все что угодно о программировании , но на самом деле правда такова"
    );
    return titles.get(new Random().nextInt(titles.size()));
  }
}
