//package com.support_App.service;
//
//
//import com.support_App.enums.Role;
//import com.support_App.exception.EmailNotFoundException;
//import com.support_App.exception.UserNotFoundException;
//import com.support_App.model.Admin;
//import com.support_App.model.Technician;
//import com.support_App.model.User;
//import com.support_App.model.UserU;
//import com.support_App.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//
//public class UserService implements UserDetailsService {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new EmailNotFoundException("User not found with username: " + email);
//        }
//        return org.springframework.security.core.userdetails.User
//                .withUsername(email)
//                .password(user.getPassword())
//                .roles(user.getRole().name())
//                .build();
//    }
//
////    public User saveUser(User user) {
////        user.setPassword(passwordEncoder.encode(user.getPassword()));
//////        user.setRole(Role.UserU);
////        return userRepository.save(user);
////    }
////
////
////    public User updateUser(User user, Long id) {
////        logger.info("Updating user with ID: {}", id);
////        logger.info("New data: {}", user);
////
////        User userToUpdate = userRepository.findById(id)
////                .orElseThrow(() -> new UserNotFoundException("user not found : " + id));
////
////        userToUpdate.setName(user.getName());
////        userToUpdate.setPassword(user.getEmail());
////
////        User updatedUser = userRepository.save(userToUpdate);
////        logger.info("Updated user: {}", updatedUser);
////
////        return updatedUser;
////    }
////
////
////    public void deleteUser(Long id) {
////        if (!userRepository.existsById(id)) {
////            throw new RuntimeException("User not found!");
////        }
////        userRepository.deleteById(id);
////    }
////
////    public List<User> getAllUsers() {
////        return userRepository.findAll();
////    }
////
////    public User getUserById(Long id) {
////        return userRepository.findById(id)
////                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
////    }
////
//////    public User createAdminUser(String name, String email, String password) {
//////        Admin admin = new Admin();
//////        admin.setName(name);
//////        admin.setEmail(email);
//////        admin.setPassword(passwordEncoder.encode(password));
//////        admin.setRole(Role.Admin);
//////        return userRepository.save(admin);
//////    }
////    public User findByEmail(String email) {
////        return userRepository.findByEmail(email);
//
//
//    public User saveUser(User user) {
//        if (user.getRole() == null) {
//            throw new IllegalArgumentException("User role must be specified.");
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//
//    public User updateUser(User user, Long id) {
//        logger.info("Updating user with ID: {}", id);
//        logger.info("New data: {}", user);
//
//        User userToUpdate = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found : " + id));
//
//        userToUpdate.setName(user.getName());
//        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        User updatedUser = userRepository.save(userToUpdate);
//        logger.info("Updated user: {}", updatedUser);
//
//        return updatedUser;
//    }
//
//    public void deleteUser(Long id) {
//        if (!userRepository.existsById(id)) {
//            throw new RuntimeException("User not found!");
//        }
//        userRepository.deleteById(id);
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public User getUserById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
//    }
//
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//}
package com.tourist.service;

import com.support_App.dto.UserDTO;
import com.support_App.enums.Role;
import com.support_App.exception.UserNotFoundException;
import com.support_App.model.Admin;
import com.support_App.model.Technician;
import com.support_App.model.User;
import com.support_App.model.UserU;
import com.support_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public User addTechnician(UserDTO userDTO) {
        Technician technician = new Technician();
        technician.setName(userDTO.getName());
        technician.setEmail(userDTO.getEmail());
        technician.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        technician.setRole(Role.Technician);
        return userRepository.save(technician);
    }

    public User addUserU(UserDTO userDTO) {
        UserU userU = new UserU();
        userU.setName(userDTO.getName());
        userU.setEmail(userDTO.getEmail());
        userU.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userU.setRole(Role.UserU);
        return userRepository.save(userU);
    }

    public User updateUser(UserDTO userDTO, Long id) {
        logger.info("Updating user with ID: {}", id);
        logger.info("New data: {}", userDTO);

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found : " + id));

        userToUpdate.setName(userDTO.getName());
        userToUpdate.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User updatedUser = userRepository.save(userToUpdate);
        logger.info("Updated user: {}", updatedUser);

        return updatedUser;
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found!");
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Technician> getAllTechnicians() {
        Role technicianRole = Role.Technician;
        List<User> users = userRepository.findByRole(Role.Technician);
        return users.stream()
                .filter(user -> user instanceof Technician)
                .map(user -> (Technician) user)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Technician getTechById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (!(user instanceof Technician)) {
            throw new UserNotFoundException("User with ID " + id + " is not a Technician.");
        }
        return (Technician) user;
    }

    public Technician updateTechnician(UserDTO userDTO, Long id) {
        logger.info("Updating technician with ID: {}", id);
        logger.info("New data: {}", userDTO);

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found : " + id));

        if (!(userToUpdate instanceof Technician technicianToUpdate)) {
            throw new UserNotFoundException("User with ID " + id + " is not a Technician.");
        }

        technicianToUpdate.setName(userDTO.getName());
        technicianToUpdate.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Technician updatedTechnician = (Technician) userRepository.save(technicianToUpdate);
        logger.info("Updated technician: {}", updatedTechnician);

        return updatedTechnician;
    }

    public void deleteTechnician(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (!(user instanceof Technician)) {
            throw new UserNotFoundException("User with ID " + id + " is not a Technician.");
        }
        userRepository.deleteById(id);
    }

}
