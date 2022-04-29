package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skillbox.socnetwork.model.entity.DeletedUser;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.repository.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeletedUserService {

  private final DeletedUsersRepository deletedUsersRepository;
  private final PersonRepository personRepository;
  private final CommentLikeRepository commentLikeRepository;
  private final MessageRepository messageRepository;
  private final NotificationRepository notificationRepository;
  private final FriendshipRepository friendshipRepository;
  private final PostLikeRepository postLikeRepository;

  @Scheduled(fixedRateString = "PT01H")
  public void checkExpiredUsers(){
    List<DeletedUser> expiredUsers = deletedUsersRepository.getAllExpired();
    if(!expiredUsers.isEmpty()){
      for(DeletedUser user : expiredUsers){
        deletePersonData(user.getPersonId());
      }
    }
  }

  public void add(Person person){
    deletedUsersRepository.addDeletedUser(person);
  }

  private void deletePersonData(Integer personId){
    commentLikeRepository.deleteAllPersonLikes(personId);
    messageRepository.deleteAllPersonMessages(personId);
    notificationRepository.deleteAllPersonNotifications(personId);
    friendshipRepository.deleteAllPersonFriendships(personId);
    postLikeRepository.deleteAllPersonLikes(personId);

    personRepository.delete(personId);
  }
}
