package org.example.bookmyshow.userservice.controller;


import jakarta.validation.Valid;
import org.example.bookmyshow.userservice.dto.UserRequestDTO;
import org.example.bookmyshow.userservice.dto.UserResponseDTO;
import org.example.bookmyshow.userservice.dto.UserUpdateRequestDTO;
import org.example.bookmyshow.userservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id) {

        UserResponseDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO request) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
