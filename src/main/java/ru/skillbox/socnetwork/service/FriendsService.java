//package ru.skillbox.socnetwork.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.skillbox.socnetwork.model.dto.PersonDto;
//import ru.skillbox.socnetwork.repository.PersonRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class FriendsService {
//
//    private final PersonRepository personRepository;
//
//    public List<PersonDto> getListRecommendedFriends() {
//        return personRepository.getListRecommendedFriends().stream()
//                .map(PersonDto::new)
//                .collect(Collectors.toList());
//    }
//
//}
