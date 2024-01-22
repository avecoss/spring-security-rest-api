package dev.alexcoss.services;

import dev.alexcoss.models.Person;
import dev.alexcoss.repositories.PeopleRepository;
import dev.alexcoss.util.Roles;
import dev.alexcoss.util.exceptions.InvalidRoleException;
import dev.alexcoss.util.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void update(int id, String role) {
        try {
            Person personToUpdate = peopleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id));

            personToUpdate.setId(id);
            if (Roles.ADMIN.getRole().equals("ROLE_" + role.toUpperCase())) {
                personToUpdate.setRole(Roles.ADMIN.getRole());
            } else if (Roles.USER.getRole().equals("ROLE_" + role.toUpperCase())) {
                personToUpdate.setRole(Roles.USER.getRole());
            } else {
                throw new InvalidRoleException("Invalid role specified: " + role);
            }

            peopleRepository.save(personToUpdate);
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }
}
