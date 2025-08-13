package com.b2b.service.impl;

import com.b2b.dto.UserDTO;
import com.b2b.dto.UserRegistrationRequest;
import com.b2b.entity.User;
import com.b2b.mapper.UserMapper;
import com.b2b.repository.UserRepository;
import com.b2b.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.b2b.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDTO registerUser(UserRegistrationRequest request) {
        try {
            log.info("Starting user registration for email: {}", request.getEmail());
            
            if (userRepository.existsByEmail(request.getEmail())) {
                log.warn("Email already registered: {}", request.getEmail());
                throw new RuntimeException("Email already registered");
            }

            User user = new User();
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setBusinessName(request.getBusinessName());
            user.setAddress(request.getAddress());
            user.setRole(request.getRole());
            user.setBlocked(false);

            User savedUser = userRepository.save(user);
            log.info("Successfully registered user with email: {}", request.getEmail());
            return userMapper.toDTO(savedUser);
        } catch (Exception e) {
            log.error("Error during user registration", e);
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    @Override
    public UserDTO authenticateUser(String email, String password) {
        try {
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            if (user.isBlocked()) {
                throw new RuntimeException("User is blocked");
            }

            return userMapper.toDTO(user);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", email, e);
            throw e;
        }
    }

    @Override
    public String generateToken(UserDTO userDTO) {
        try {
            log.info("Generating token for user: {}", userDTO.getEmail());
            return jwtTokenProvider.generateToken(userDTO.getEmail());
        } catch (Exception e) {
            log.error("Error generating token for user: {}", userDTO.getEmail(), e);
            throw new RuntimeException("Error generating authentication token", e);
        }
    }

    @Override
    public UserDTO authenticateWithGoogle(String googleToken) {
        try {
            GoogleIdToken.Payload payload = verifyGoogleToken(googleToken);
            String email = payload.getEmail();
            return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not registered"));
        } catch (Exception e) {
            log.error("Google authentication failed", e);
            throw new RuntimeException("Google authentication failed: " + e.getMessage());
        }
    }

    @Override
    public UserDTO registerWithGoogle(String googleToken) {
        try {
            GoogleIdToken.Payload payload = verifyGoogleToken(googleToken);
            String email = payload.getEmail();

            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email already registered");
            }

            User user = new User();
            user.setEmail(email);
            user.setFullName((String) payload.get("name"));
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            user.setRole(User.Role.USER); // Default role for Google registration
            user.setBlocked(false);

            User savedUser = userRepository.save(user);
            return userMapper.toDTO(savedUser);
        } catch (Exception e) {
            log.error("Google registration failed", e);
            throw new RuntimeException("Google registration failed: " + e.getMessage());
        }
    }

    private GoogleIdToken.Payload verifyGoogleToken(String tokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("your-google-client-id"))
                .build();

            GoogleIdToken token = verifier.verify(tokenString);
            if (token == null) {
                throw new RuntimeException("Invalid Google token");
            }

            return token.getPayload();
        } catch (Exception e) {
            log.error("Failed to verify Google token", e);
            throw new RuntimeException("Failed to verify Google token", e);
        }
    }

    // Implementation of other interface methods
    @Override
    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        userMapper.updateUserFromDTO(userDTO, user);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void blockUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBlocked(true);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBlocked(false);
        userRepository.save(user);
    }
}