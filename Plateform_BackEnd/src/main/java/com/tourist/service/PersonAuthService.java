package com.tourist.service;

import com.tourist.dto.AuthRequestDTO;
import com.tourist.dto.JwtResponseDTO;
import com.tourist.enums.Role;
import com.tourist.model.Person;
import com.tourist.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonAuthService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Lazy
    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(person.getEmail())
                .password(person.getPassword())
                .roles(person.getRole().name())
                .build();
    }

    public JwtResponseDTO signUp(Person personRequest) {
        if (personRepository.findByEmail(personRequest.getEmail()) != null) {
            throw new RuntimeException("Email is already taken.");
        }
        if (personRequest.getRole() == null) {
            personRequest.setRole(Role.Client);
        }
        personRequest.setPassword(passwordEncoder.encode(personRequest.getPassword()));
        Person savedPerson = personRepository.save(personRequest);

        String token = jwtService.generateToken(savedPerson.getEmail(), savedPerson.getRole());

        return JwtResponseDTO.builder()
                .accessToken(token)
                .person(savedPerson)
                .build();
    }

    public JwtResponseDTO login(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Person person = personRepository.findByEmail(authRequestDTO.getEmail());
            String token = jwtService.generateToken(person.getEmail(), person.getRole());
            return JwtResponseDTO.builder()
                    .accessToken(token)
                    .person(person)
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request.");
        }
    }
}
