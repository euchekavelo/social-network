package ru.skillbox.socnetwork.service;

import com.dropbox.core.DbxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.model.rqdto.RegisterDto;
import ru.skillbox.socnetwork.model.rqdto.LoginDto;
import ru.skillbox.socnetwork.model.rsdto.PersonDto;
import ru.skillbox.socnetwork.model.rsdto.UpdatePersonDto;
import ru.skillbox.socnetwork.repository.PersonRepository;
import ru.skillbox.socnetwork.security.JwtTokenProvider;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider tokenProvider;
    private final PhotoStorageService photoStorageService;

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
            person.setPhoto(photoStorageService.getDefaultProfileImage());
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return saveFromRegistration(person);
    }

    public PersonDto getPersonAfterLogin(LoginDto loginDto) {
        Person person = personRepository.getPersonAfterLogin(loginDto);
        if (person == null) {
            return null;
        } else {
        return new PersonDto(person,
                tokenProvider.generateToken(loginDto.getEmail()));
        }
    }
    public Person updatePerson(UpdatePersonDto changedPerson, Person updatablePerson){
        if(changedPerson.getFirstName() != null &&
            !changedPerson.getFirstName().equals(updatablePerson.getFirstName())){
            updatablePerson.setFirstName(changedPerson.getFirstName());
        }
        if(changedPerson.getLastName() != null &&
            !changedPerson.getLastName().equals(updatablePerson.getLastName())){
            updatablePerson.setLastName(changedPerson.getLastName());
        }
        String date = "";
        if(changedPerson.getBirthDate() != null){
            date = changedPerson.getBirthDate().substring(0, 10);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            long dateTime = 0;
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
}
