package ru.skillbox.socnetwork.service;

import com.dropbox.core.DbxException;
import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.controller.exception.InvalidRequestException;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.entity.TempToken;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.UpdatePersonDto;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.JwtTokenProvider;
import ru.skillbox.socnetwork.security.SecurityUser;
import ru.skillbox.socnetwork.service.storage.StorageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider tokenProvider;
    private final StorageService storageService;
    private final TempTokenService tempTokenService;
    private final MailService mailService;

    public List<Person> getAll() {
        return personRepository.getAll();
    }

    public Person getByEmail(String email) {
        return personRepository.getByEmail(email);
    }

    public boolean isEmptyEmail(String email) {
        return personRepository.isEmptyEmail(email);
    }

    public Person saveFromRegistration(Person person) {
        return personRepository.saveFromRegistration(person);
    }

    public Person getById(int id) {
        return personRepository.getById(id);
    }

    public Person getPersonAfterRegistration(RegisterDto registerDto) {
        if (!registerDto.passwordsEqual() || !isEmptyEmail(registerDto.getEmail())) {
            return null;
        }
        //TODO вынести создание персона из RegisterDto в Person?
        Person person = new Person();
        person.setEmail(registerDto.getEmail());
        person.setPassword(new BCryptPasswordEncoder().encode(registerDto.getSecondPassword()));
        person.setFirstName(registerDto.getFirstName());
        person.setLastName(registerDto.getLastName());
        try {
            person.setPhoto(storageService.getDefaultProfileImage());
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return saveFromRegistration(person);
    }

    public PersonDto getPersonAfterLogin(LoginDto loginDto) {
        Person person = personRepository.getPersonAfterLogin(loginDto);
        if (person == null || person.getIsBlocked()) {
            return null;
        } else {
            return new PersonDto(person,
                tokenProvider.generateToken(loginDto.getEmail()));
        }
    }
    public Person updatePerson(UpdatePersonDto changedPerson){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        String email = securityUser.getUsername();
        Person updatablePerson = getByEmail(email);

        if(changedPerson.getFirstName() != null &&
            !changedPerson.getFirstName().equals(updatablePerson.getFirstName())){
            updatablePerson.setFirstName(changedPerson.getFirstName());
        }
        if(changedPerson.getLastName() != null &&
            !changedPerson.getLastName().equals(updatablePerson.getLastName())){
            updatablePerson.setLastName(changedPerson.getLastName());
        }
        String date;
        if(changedPerson.getBirthDate() != null){
            date = changedPerson.getBirthDate().substring(0, 10);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            long dateTime;
            try {
                dateTime = format.parse(date).getTime();
                if(dateTime != updatablePerson.getBirthDate()){
                    updatablePerson.setBirthDate(dateTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!changedPerson.getPhone().isEmpty()){
            String phone = (changedPerson.getPhone().charAt(0) == '7') ? changedPerson.getPhone() :
                "7" + changedPerson.getPhone();
            if(!phone.equals(updatablePerson.getPhone())) {
                updatablePerson.setPhone(phone);
            }
        }
        if(changedPerson.getAbout() != null &&
            !changedPerson.getAbout().equals(updatablePerson.getAbout())){
            updatablePerson.setAbout(changedPerson.getAbout());
        }
        if(changedPerson.getCity() != null &&
            !changedPerson.getCity().equals(updatablePerson.getCity())){
            updatablePerson.setCity(changedPerson.getCity());
        }
        if(changedPerson.getCountry() != null &&
            !changedPerson.getCountry().equals(updatablePerson.getCountry())){
            updatablePerson.setCountry(changedPerson.getCountry());
        }
        return personRepository.updatePerson(updatablePerson);
    }

    public void updatePhoto(String photo, Person person){
        person.setPhoto(photo);
        personRepository.updatePhoto(person);
    }

    public String updateEmail(Map<String, String> body) throws InvalidRequestException{
        String email = tempTokenService.getToken(body.get("token")).getEmail();
        Person person = getByEmail(email);
        if(person == null){
            throw new InvalidRequestException("User with email " + email + " not registered");
        }
        personRepository.updateEmail(person, body.get("email"));
        tempTokenService.deleteToken(body.get("token"));
        return "ok";
    }

    public List<PersonDto> getPersonsBySearchParameters(String firstName, String lastName,
        long ageFrom, long ageTo,
        int countryId, int cityId,
        int perPage) {
        List<Person> persons = personRepository.getPersonsFromSearch(firstName, lastName, ageFrom, ageTo,
            countryId, cityId, perPage);

        List<PersonDto> personsDto = new ArrayList<>();
        for (Person person : persons) {
            personsDto.add(new PersonDto(person));
        }
        return personsDto;
    }

    public String recoverEmail(String email) throws InvalidRequestException {
        Person person = getByEmail(email);
        if(person == null){
            throw new InvalidRequestException("User with email " + email + " not registered");
        }
        TempToken token = new TempToken(person.getEmail(), generateToken());
        tempTokenService.addToken(token);
        String link = "localhost:8086/shift-email?token=" + token.getToken();
        mailService.send(email, "Your SocNetwork Email change link", link);
        return "ok";
    }

    public String recoverPassword(String email) throws InvalidRequestException {
        Person person = getByEmail(email);
        if(person == null){
            throw new InvalidRequestException("User with email " + email + " not registered");
        }
        TempToken token = new TempToken(person.getEmail(), generateToken());
        tempTokenService.addToken(token);
        String link = "localhost:8086/change-password?token=" + token.getToken();
        mailService.send(person.getEmail(), "SocNetwork Password recovery", link);
        return "ok";
    }

    public String setPassword(Map<String, String> body) throws InvalidRequestException{
        if(body.get("token") == null){
            throw new InvalidRequestException("wrong recovery link");
        }
        TempToken token = tempTokenService.getToken(body.get("token"));
        if(token == null){
            throw new InvalidRequestException("invalid recovery token");
        }
        Person person = getByEmail(token.getEmail());
        person.setPassword(new BCryptPasswordEncoder().encode(body.get("password")));
        personRepository.updatePassword(person);
        tempTokenService.deleteToken(body.get("token"));
        return "ok";
    }

    private String generateToken(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(65, 90).build();
        return generator.generate(10);
    }

//    public void setBlockPerson(Person person, Boolean block){
//        personRepository.setBlockPerson(person, block);
//    }
//
//    public void deletePerson(Person person){
//        personRepository.deletePerson(person);
//    }
}
