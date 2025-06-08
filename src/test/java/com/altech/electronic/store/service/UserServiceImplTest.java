package com.altech.electronic.store.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.altech.electronic.store.dto.UserDTO;
import com.altech.electronic.store.dto.UserRegistrationDTO;
import com.altech.electronic.store.dto.UserResponseDTO;
import com.altech.electronic.store.model.User;
import com.altech.electronic.store.repository.UserRepository;
import com.altech.electronic.store.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtService jwtService;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    Authentication mockAuthentication;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userReqDTO;
    private UserRegistrationDTO userRegistrationDTO;
    private UserResponseDTO userRespDTO;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("Test@abc.com");
        userReqDTO = new UserDTO();
        userRespDTO = new UserResponseDTO();
        userRespDTO.setId(1L);
        userRegistrationDTO= new UserRegistrationDTO();
        userRegistrationDTO.setEmail("Test@abc.com");
        userRegistrationDTO.setPassword("ABC@");
        userRegistrationDTO.setFirstName("Test");
    }

    @Test
    public void testGetUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(userRespDTO.getId(), result.getId());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAllByIsDeletedFalse()).thenReturn(users);
        List<User> result = userService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userRespDTO.getId(), result.get(0).getId());
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("123456789");
        when(jwtService.generateToken(any())).thenReturn("123456789");
        UserResponseDTO result = userService.register(userRegistrationDTO);
        assertNotNull(result);
        assertEquals(userRespDTO.getId(), result.getId());
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        UserDTO result = userService.updateUser(userReqDTO);
        assertNotNull(result);
        assertEquals(userRespDTO.getId(), result.getId());
    }
    
    @Test
    public void testAuthenticate() { 
    	when(mockAuthentication.getPrincipal()).thenReturn(user);
    	when(jwtService.generateToken(any())).thenReturn("123456789");
    	when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        UserResponseDTO result = userService.authenticate(userRegistrationDTO.getEmail(), userRegistrationDTO.getPassword());
        assertNotNull(result);
        assertEquals(userRespDTO.getId(), result.getId());
    }

}