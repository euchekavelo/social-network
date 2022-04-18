package ru.skillbox.socnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socnetwork.model.rsdto.GeneralResponse;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users/search")
public class SearchPostController {

    private final PersonService personService;

    @GetMapping()
    public ResponseEntity<GeneralResponse<List<PersonDto>>> searchByPeople(@RequestParam(value = "first_name", defaultValue = "", required = false) String firstName,
                                                                           @RequestParam(value = "last_name", defaultValue = "", required = false) String lastName,
                                                                           @RequestParam(value = "age_from", defaultValue = "0", required = false) long ageFrom,
                                                                           @RequestParam(value = "age_to", defaultValue = "150", required = false) long ageTo,
                                                                           @RequestParam(value = "country_id", defaultValue = "1", required = false) int countryId,
                                                                           @RequestParam(value = "city_id", defaultValue = "1", required = false) int cityId,
                                                                           @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
                                                                           @RequestParam(value = "itemPerPage", defaultValue = "20", required = false) int perPage) {
        GeneralResponse<List<PersonDto>> response = new GeneralResponse<>
                (personService.getPersonsBySearchParameters(firstName, lastName, ageFrom, ageTo,
                        countryId, cityId, perPage));
        return ResponseEntity.ok(response);
    }
}
