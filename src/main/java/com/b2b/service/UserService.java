package com.b2b.service;

import com.b2b.dto.UserDTO;
import com.b2b.dto.UserRegistrationRequest;
import com.b2b.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UUID id, UserDTO userDTO);
    void deleteUser(UUID id);
    void blockUser(UUID id);
    void unblockUser(UUID id);
    UserDTO registerUser(UserRegistrationRequest request);
    UserDTO authenticateUser(String email, String password);
    UserDTO authenticateWithGoogle(String googleToken);
    UserDTO registerWithGoogle(String googleToken);
    String generateToken(UserDTO userDTO);
}
