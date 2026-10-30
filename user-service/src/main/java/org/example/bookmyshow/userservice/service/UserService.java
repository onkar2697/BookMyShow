package org.example.bookmyshow.userservice.service;


import org.example.bookmyshow.userservice.dto.UserRequestDTO;
import org.example.bookmyshow.userservice.dto.UserResponseDTO;
import org.example.bookmyshow.userservice.dto.UserUpdateRequestDTO;
import org.example.bookmyshow.userservice.entity.Role;
import org.example.bookmyshow.userservice.entity.User;
import org.example.bookmyshow.userservice.exception.DuplicateEmailException;
import org.example.bookmyshow.userservice.exception.UserNotFoundException;
import org.example.bookmyshow.userservice.mapper.UserMapper;
import org.example.bookmyshow.userservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository,PasswordEncoder passwordEncoder) {
       this.userMapper = userMapper;
       this.passwordEncoder=passwordEncoder;
       this.userRepository=userRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request){
            if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
                throw new DuplicateEmailException(
                        "Email already registered: " + request.getEmail()
                );
            }

            User user = userMapper.toEntity(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);

            User savedUser = userRepository.save(user);

            return userMapper.toResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        ));

        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        ));

        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        ));

        // Check whether the email is changing
        if (!user.getEmail().equalsIgnoreCase(request.getEmail())) {

            // Check whether another user already owns the new email
            if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
                throw new DuplicateEmailException(
                        "Email already registered: " + request.getEmail()
                );
            }
        }

        // Update user details
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Save updated user
        User savedUser = userRepository.save(user);

        // Convert entity to response DTO
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        ));

        userRepository.delete(user);
    }


}
