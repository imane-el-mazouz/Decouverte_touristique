package com.tourist.service;

import com.tourist.dto.UserDto;
import com.tourist.exception.UserNotFoundException;
import com.tourist.model.User;
import com.tourist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

  public UserDto saveUser(UserDto userDto) {
    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setName(userDto.getName());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setProfession(userDto.getPhone());
    user.setProfession(userDto.getProfession());
    User savedUser = userRepository.save(user);
    return new UserDto(
      savedUser.getIdU(),
      savedUser.getEmail(),
      savedUser.getName(),
      savedUser.getPassword(),
      savedUser.getPhone(),
      savedUser.getProfession()

      );
  }

  public User findUserById(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    return userOptional.orElseThrow(() -> new UserNotFoundException("User not found!"));
  }

  public UserDto updateUser(UserDto userDto, Long id) {
        User userUpdated = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found !"));
        userUpdated.setName(userDto.getName());
        userUpdated.setEmail(userDto.getEmail());
        userUpdated.setPhone(userDto.getEmail());
        userUpdated.setProfession(userDto.getProfession());
        userRepository.save(userUpdated);

        return convertToDto(userUpdated);
    }
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getIdU(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getProfession(),
                user.getPhone()
        );
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getIdU(), user.getEmail(), user.getName(), user.getPassword(), user.getProfession(), user.getPhone()))
                .collect(Collectors.toList());
    }
}

//package com.e_bank.service;
//
//import com.e_bank.dto.AuthRequestDTO;
//import com.e_bank.dto.JwtResponseDTO;
//import com.e_bank.dto.UserDto;
//import com.e_bank.exception.DatabaseEmptyException;
//import com.e_bank.exception.UserNotFoundException;
//import com.e_bank.mapper.UserMapper;
//import com.e_bank.model.User;
//import com.e_bank.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * Service layer for managing users in the E-Bank application.
// */
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserMapper userMapper;
//
//
//    /**
//     * Retrieves all users from the database.
//     *
//     * @return List of users
//     * @throws DatabaseEmptyException if no users are found
//     */
//    public List<User> getAll() {
//        var users = userRepository.findAll();
//        if (users.isEmpty()) {
//            throw new DatabaseEmptyException();
//        }
//        return users;
//    }
//
//    /**
//     * Saves a new user.
//     *
//     * @param userDto User data to be saved
//     * @return Saved user DTO
//     */
//    public UserDto save(UserDto userDto) {
//        var user = userMapper.toUser(userDto);
//        return userMapper.toUserDto(userRepository.save(user));
//    }
//
//    /**
//     * Updates an existing user.
//     *
//     * @param userDto User data to update
//     * @param id      ID of the user to update
//     * @return Updated user DTO
//     * @throws UserNotFoundException if the user with given ID is not found
//     */
//    public UserDto update(UserDto userDto, Long id) throws UserNotFoundException {
//        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
//        var userUpdated = userMapper.updateUserFromDto(userDto, user);
//        return userMapper.toUserDto(userRepository.save(userUpdated));
//    }
//
//    /**
//     * Retrieves a user by ID.
//     *
//     * @param id ID of the user to retrieve
//     * @return User entity
//     * @throws UserNotFoundException if the user with given ID is not found
//     */
//    public User getById(Long id) throws UserNotFoundException {
//        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
//    }
//
//    /**
//     * Deletes a user by ID.
//     *
//     * @param id ID of the user to delete
//     * @return Deleted user DTO
//     * @throws UserNotFoundException if the user with given ID is not found
//     */
//    public UserDto delete(Long id) throws UserNotFoundException {
//        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
//        userRepository.delete(user);
//        return userMapper.toUserDto(user);
//    }
//}
