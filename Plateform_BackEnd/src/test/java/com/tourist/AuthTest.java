//package com.tourist;
//
//import com.tourist.dto.AuthRequestDTO;
//import com.tourist.dto.JwtResponseDTO;
//import com.tourist.enums.Role;
//import com.tourist.model.Person;
//import com.tourist.repository.PersonRepository;
//import com.tourist.service.JwtService;
//import com.tourist.service.PersonAuthService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class AuthTest {
//
//    @Mock
//    private PersonRepository personRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private JwtService jwtService;
//
//    @InjectMocks
//    private PersonAuthService personAuthService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testLoadUserByUsername_UserNotFound() {
//        when(personRepository.findByEmail("test@example.com")).thenReturn(null);
//
//        assertThrows(UsernameNotFoundException.class, () -> personAuthService.loadUserByUsername("test@example.com"));
//    }
//
//    @Test
//    void testLogin_InvalidCredentials() {
//        AuthRequestDTO authRequest = new AuthRequestDTO("test@example.com", "wrongpassword");
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenThrow(new UsernameNotFoundException("Invalid user request."));
//
//        assertThrows(UsernameNotFoundException.class, () -> personAuthService.login(authRequest));
//    }
//}
