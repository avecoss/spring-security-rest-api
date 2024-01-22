package dev.alexcoss.controllers;

import dev.alexcoss.models.Person;
import dev.alexcoss.security.PersonDetails;
import dev.alexcoss.services.AdminService;
import dev.alexcoss.util.PersonErrorResponse;
import dev.alexcoss.util.Roles;
import dev.alexcoss.util.exceptions.InvalidRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PeopleController {
    private final AdminService adminService;

    @Autowired
    public PeopleController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> users = adminService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user")
    public ResponseEntity<Person> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return ResponseEntity.ok(personDetails.getPerson());
    }

    @PatchMapping("/change_role")
    public ResponseEntity<?> changeUserRole(@RequestBody Map<String, String> requestBody) {
        try {
            int id = Integer.parseInt(requestBody.get("id"));
            String role = requestBody.get("role");
            System.out.println(role);

            validateRole(role);

            adminService.update(id, role);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new PersonErrorResponse("Invalid ID specified.", System.currentTimeMillis()));
        } catch (InvalidRoleException e) {
            return ResponseEntity.badRequest().body(new PersonErrorResponse("Invalid role specified.", System.currentTimeMillis()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PersonErrorResponse("Internal server error.", System.currentTimeMillis()));
        }
    }

    private void validateRole(String role) {
        boolean isValidRole = Arrays.stream(Roles.values())
            .anyMatch(r -> r.getRole().equals("ROLE_" + role.toUpperCase()));

        if (!isValidRole) {
            throw new InvalidRoleException("Invalid role specified.");
        }
    }
}
