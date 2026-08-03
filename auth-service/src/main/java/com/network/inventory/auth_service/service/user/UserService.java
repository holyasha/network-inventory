package com.network.inventory.auth_service.service.user;

import java.util.List;

import com.network.inventory.auth_service.dto.request.user.CreateUserRequest;
import com.network.inventory.auth_service.dto.request.user.UpdateUserRequest;
import com.network.inventory.auth_service.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByLogin(String login);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
}
