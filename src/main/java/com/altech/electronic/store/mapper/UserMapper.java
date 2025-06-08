package com.altech.electronic.store.mapper;

import java.util.ArrayList;
import java.util.List;

import com.altech.electronic.store.dto.UserDTO;
import com.altech.electronic.store.dto.UserRegistrationDTO;
import com.altech.electronic.store.dto.UserResponseDTO;
import com.altech.electronic.store.model.User;

public class UserMapper {

    public static User fromDTO(UserDTO userDTO) {
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .build();
    }

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
    
    public static User fromRegistrationDTO(UserRegistrationDTO registrationDTO) {
        return User.builder()
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .email(registrationDTO.getEmail())
                .phoneNumber(registrationDTO.getPhoneNumber())
                .address(registrationDTO.getAddress())
                .build();
    }

    public static UserResponseDTO toResponseDTO(User user, String token) {
        return UserResponseDTO.builder()
        		.id(user.getId())
        		.role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .token(token)
                .build();
    }

    public static void updateFromDTO(UserDTO userDTO, User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
    }

    public static List<UserDTO> toDTOs(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(toDTO(user));
        }
        return userDTOs;
    }
}