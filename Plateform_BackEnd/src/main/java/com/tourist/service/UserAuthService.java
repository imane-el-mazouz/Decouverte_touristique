package com.tourist.service;


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

import java.util.ArrayList;

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
//@Override
//public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    User user = userRepository.findByEmail(email);
//    if (user == null) {
//        throw new UsernameNotFoundException("User not found with email: " + email);
//    }
//    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
//}

    //    public JwtResponseDTO signUp(User userRequest) {
//        if (userRepository.findByEmail(userRequest.getName()) != null) {
//            throw new RuntimeException("Username is already taken.");
//        }
//        if (userRequest.getRole() == null) {
//            userRequest.setRole(Role.UserU);
//        }
//        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//
//        User savedUser = userRepository.save(userRequest);
//        String token = jwtService.generateToken(savedUser.getName(),savedUser.getRole());
//
//        return JwtResponseDTO.builder()
//                .accessToken(token)
//                .user(savedUser)
//                .build();
//    }
public JwtResponseDTO signUp(Person personRequest) {
    if (personRepository.findByEmail(personRequest.getEmail()) != null) {
        throw new RuntimeException("Email is already taken.");
    }
    if (personRequest.getRole() == null) {
        personRequest.setRole(Role.Admin);
    } else {
        try {
            personRequest.setRole(Role.valueOf(personRequest.getRole().name()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + personRequest.getRole().name());
        }
    }
    personRequest.setPassword(passwordEncoder.encode(personRequest.getPassword()));
    Person savedPerson = personRepository.save(personRequest);

    String token = jwtService.generateToken(personRequest.getEmail(), savedPersongetRole());

    return JwtResponseDTO.builder()
            .accessToken(token)
            .person(savedPerson)
            .build();
}

    private Role savedPersongetRole() {
    }

    public JwtResponseDTO login(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Person person =personRepository.findByEmail(authRequestDTO.getEmail());
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
