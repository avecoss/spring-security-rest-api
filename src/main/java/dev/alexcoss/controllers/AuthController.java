package dev.alexcoss.controllers;

import dev.alexcoss.dto.LoginDTO;
import dev.alexcoss.dto.SingUpDTO;
import dev.alexcoss.models.Person;
import dev.alexcoss.security.JWTUtil;
import dev.alexcoss.services.RegistrationService;
import dev.alexcoss.util.PersonErrorResponse;
import dev.alexcoss.util.PersonValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(loginDTO.getUsername());
            return ResponseEntity.ok(Map.of("jwt-token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new PersonErrorResponse("Incorrect credentials!", System.currentTimeMillis()));
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid SingUpDTO singUpDTO, BindingResult bindingResult) {
        Person person = convertToPerson(singUpDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream().forEach(e -> {
                errorMsg.append(e.getField())
                    .append(" - ")
                    .append(e.getDefaultMessage())
                    .append("; ");
            });
            return ResponseEntity.badRequest().body(new PersonErrorResponse(errorMsg.toString(), System.currentTimeMillis()));
        }

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());
        return ResponseEntity.ok(Map.of("jwt-token", token, "message", "User registered successfully!"));
    }

    public Person convertToPerson(SingUpDTO singUpDTO) {
        return this.modelMapper.map(singUpDTO, Person.class);
    }
}
