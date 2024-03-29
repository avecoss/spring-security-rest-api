package dev.alexcoss.services;

import dev.alexcoss.models.Person;
import dev.alexcoss.repositories.PeopleRepository;
import dev.alexcoss.util.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);
        person.setRole(Roles.USER.getRole());

        peopleRepository.save(person);
    }
}
