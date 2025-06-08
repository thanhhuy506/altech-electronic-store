package com.altech.electronic.store.service;

import com.altech.electronic.store.exception.EmailAlreadyExistsException;
import com.altech.electronic.store.model.User;

import dto.UserDTO;
import dto.UserRegistrationDTO;
import dto.UserResponseDTO;

public interface UserService extends BaseService<User>{

	UserResponseDTO register(UserRegistrationDTO registrationDTO) throws EmailAlreadyExistsException;
    UserResponseDTO authenticate(String email, String password);
    UserDTO getCurrentUser();
    UserDTO getUserById(Long id);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    
}
