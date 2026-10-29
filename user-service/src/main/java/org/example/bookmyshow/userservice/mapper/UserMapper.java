package org.example.bookmyshow.userservice.mapper;

import org.example.bookmyshow.userservice.dto.UserRequestDTO;
import org.example.bookmyshow.userservice.dto.UserResponseDTO;
import org.example.bookmyshow.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toEntity(UserRequestDTO request) {
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Password encoding will be handled in UserServiceImpl.
        // Do not accept role from the request.
        // User's default role should remain USER.

        return user;
    }

        public UserResponseDTO toResponseDTO(User user) {
            UserResponseDTO response = new UserResponseDTO();

            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole());
            response.setCreateTime(user.getCreatedAt());

            // Never include the password in the response.

            return response;
        }
}
