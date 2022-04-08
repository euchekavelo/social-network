package ru.skillbox.socnetwork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socnetwork.controller.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.entity.Friendship;
import ru.skillbox.socnetwork.model.entity.enums.TypeCode;
import ru.skillbox.socnetwork.model.rsdto.MessageResponseDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.repository.FriendshipRepository;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.SecurityUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsService {

    private final PersonRepository personRepository;
    private final FriendshipRepository friendshipRepository;

    private SecurityUser getAuthorizedUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<PersonDto> getListRecommendedFriends() {
        String email = getAuthorizedUser().getUsername();
        return personRepository.getListRecommendedFriends(email).stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());
    }

    public List<PersonDto> getUserFriends() {
        String email = getAuthorizedUser().getUsername();
        return personRepository.getUserFriends(email).stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageResponseDto deleteFriendById(Integer friendId) {
        String email = getAuthorizedUser().getUsername();
        Integer authorizedUserId = personRepository.getByEmail(email).getId();
        friendshipRepository.removeFriendlyStatusByPersonIds(authorizedUserId, friendId);
        friendshipRepository.removeFriendlyStatusByPersonIds(friendId, authorizedUserId);
        return new MessageResponseDto("ok");
    }

    public MessageResponseDto addFriendById(Integer focusPersonId) throws InvalidRequestException {
        String email = getAuthorizedUser().getUsername();
        Integer authorizedUserId = personRepository.getByEmail(email).getId();
        if (authorizedUserId.equals(focusPersonId)) {
            throw new InvalidRequestException("You can't send a request to yourself.");
        }

        Optional<Friendship> friendshipFromInitiator = friendshipRepository
                .getFriendlyStatusByPersonIdsAndCode(authorizedUserId, focusPersonId, TypeCode.FRIEND.toString());
        Optional<Friendship> friendshipFromFocusPerson = friendshipRepository
                .getFriendlyStatusByPersonIdsAndCode(focusPersonId, authorizedUserId, TypeCode.FRIEND.toString());
        if (friendshipFromInitiator.isPresent() || friendshipFromFocusPerson.isPresent()) {
            throw new InvalidRequestException("It is not possible to apply as a friend, " +
                    "because these users are already friends.");
        }

        Optional<Friendship> initiatedRequest = friendshipRepository
                .getFriendlyStatusByPersonIdsAndCode(authorizedUserId, focusPersonId, TypeCode.REQUEST.toString());
        Optional<Friendship> receivedRequest = friendshipRepository
                .getFriendlyStatusByPersonIdsAndCode(focusPersonId, authorizedUserId, TypeCode.REQUEST.toString());

        if (initiatedRequest.isEmpty() && receivedRequest.isEmpty()) {
            friendshipRepository.createFriendRequestByPersonIds(authorizedUserId, focusPersonId);
        } else if (initiatedRequest.isEmpty()) {
            friendshipRepository.updateFriendlyStatusByPersonIdsAndCode(focusPersonId, authorizedUserId,
                    TypeCode.FRIEND.toString());
        } else {
            throw new InvalidRequestException("It is not possible to submit a request to add as a friend, " +
                    "as it has already been submitted earlier.");
        }

        return new MessageResponseDto("ok");
    }

    public List<PersonDto> getListIncomingFriendRequests() {
        String email = getAuthorizedUser().getUsername();
        return personRepository.getListIncomingFriendRequests(email).stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());
    }

}
