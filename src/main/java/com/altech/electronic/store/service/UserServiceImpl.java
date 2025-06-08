package com.altech.electronic.store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altech.electronic.store.enums.RoleEnum;
import com.altech.electronic.store.exception.EmailAlreadyExistsException;
import com.altech.electronic.store.exception.ResourceNotFoundException;
import com.altech.electronic.store.mapper.UserMapper;
import com.altech.electronic.store.model.User;
import com.altech.electronic.store.repository.BaseRepository;
import com.altech.electronic.store.repository.UserRepository;
import com.altech.electronic.store.security.JwtService;

import dto.UserDTO;
import dto.UserRegistrationDTO;
import dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional
    public UserResponseDTO register(UserRegistrationDTO registrationDTO) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        User user = UserMapper.fromRegistrationDTO(registrationDTO);
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRole(RoleEnum.CUSTOMER);
        
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(user);
        
        return UserMapper.toResponseDTO(savedUser, token);
    }

    @Override
    public UserResponseDTO authenticate(String email, String password) {
    	logger.info("==Start authenticate");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        logger.info("==End authenticate");
        return UserMapper.toResponseDTO(user, token);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return UserMapper.toDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return UserMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", userDTO.getId()));
        
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setAddress(userDTO.getAddress());
        
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        user.setIsDeleted(true);
        userRepository.save(user);
    }

	@Override
	protected BaseRepository<User> getRepository() {
		return userRepository;
	}

}
