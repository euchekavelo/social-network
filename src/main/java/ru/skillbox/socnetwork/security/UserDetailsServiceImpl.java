package ru.skillbox.socnetwork.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socnetwork.logging.DebugLogs;
import ru.skillbox.socnetwork.model.entity.Person;
import ru.skillbox.socnetwork.service.PersonService;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonService personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.getByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException("user " + email + " "
                    + "not found");
        }
        return setUserDetails(person);
    }

    public SecurityUser setUserDetails(Person person) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
        return SecurityUser.builder()
                .id(person.getId())
                .username(person.getEmail())
                .password(person.getPassword())
                .authorities(authorities)
                .build();
    }
}
