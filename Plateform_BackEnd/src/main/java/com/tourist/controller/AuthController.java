package com.tourist.controller;

import com.tourist.dto.AuthRequestDTO;
import com.tourist.dto.JwtResponseDTO;
import com.tourist.dto.UserDto;
import com.tourist.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")

@RequiredArgsConstructor
public class AuthController {


    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        try {
            JwtResponseDTO jwtResponseDTO = userAuthService.login(authRequestDTO);
            return ResponseEntity.ok(jwtResponseDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

  @PostMapping("/signup")
  public ResponseEntity<JwtResponseDTO> registerUser(@RequestBody UserDto userDto) {
    JwtResponseDTO response = userAuthService.signUp(userDto);
    return ResponseEntity.ok(response);
  }

//    @PostMapping("/signup")
//    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
//        try {
//            var jwtResponseDTO = userAuthService.signUp(userDto);
//            return ResponseEntity.ok(jwtResponseDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
}
