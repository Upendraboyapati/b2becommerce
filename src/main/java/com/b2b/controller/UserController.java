package com.b2b.controller;

import com.b2b.dto.*;
import com.b2b.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5174") // For Vite frontend
public class UserController {

    private final UserService userService;

    // Authentication endpoints
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegistrationRequest request) {
        UserDTO userDTO = userService.registerUser(request);
        String token = userService.generateToken(userDTO);
        return ResponseEntity.ok(new AuthResponse(token, userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        UserDTO userDTO = userService.authenticateUser(request.getEmail(), request.getPassword());
        String token = userService.generateToken(userDTO);
        return ResponseEntity.ok(new AuthResponse(token, userDTO));
    }

    @PostMapping("/google/login")
    public ResponseEntity<AuthResponse> googleLogin(@RequestBody GoogleAuthRequest request) {
        UserDTO userDTO = userService.authenticateWithGoogle(request.getCredential());
        String token = userService.generateToken(userDTO);
        return ResponseEntity.ok(new AuthResponse(token, userDTO));
    }

    @PostMapping("/google/register")
    public ResponseEntity<AuthResponse> googleRegister(@RequestBody GoogleAuthRequest request) {
        UserDTO userDTO = userService.registerWithGoogle(request.getCredential());
        String token = userService.generateToken(userDTO);
        return ResponseEntity.ok(new AuthResponse(token, userDTO));
    }

    // Your existing endpoints
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable UUID id) {
        userService.blockUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable UUID id) {
        userService.unblockUser(id);
        return ResponseEntity.noContent().build();
    }
}