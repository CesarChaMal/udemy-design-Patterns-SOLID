package com.balazsholczer.dto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    
    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.isActive()
        );
    }
    
    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setActive(dto.isActive());
        return user;
    }
    
    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                   .map(UserMapper::toDTO)
                   .collect(Collectors.toList());
    }
    
    public static List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream()
                  .map(UserMapper::toEntity)
                  .collect(Collectors.toList());
    }
}