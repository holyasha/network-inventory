package com.network.inventory.auth_service.service.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.network.inventory.auth_service.dto.request.user.CreateUserRequest;
import com.network.inventory.auth_service.dto.request.user.UpdateUserRequest;
import com.network.inventory.auth_service.dto.response.UserResponse;
import com.network.inventory.auth_service.entity.Role;
import com.network.inventory.auth_service.entity.User;
import com.network.inventory.auth_service.exeption.DuplicateResourceException;
import com.network.inventory.auth_service.exeption.ResourceNotFoundException;
import com.network.inventory.auth_service.repository.RoleRepository;
import com.network.inventory.auth_service.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByLogin(request.login()).isPresent()) {
            throw new DuplicateResourceException("Пользователь с логином " + request.login() + " уже существует");
        }
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("Пользователь с email " + request.email() + " уже существует");
        }

        Set<Role> roles = new HashSet<>();
        for (Long roleId : request.roleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Роль не найдена с id: " + roleId));
            roles.add(role);
        }

        User user = new User(request.login(), passwordEncoder.encode(request.password()), request.email());
        user.setEnabled(request.enabled() != null ? request.enabled() : true);
        user.setRoles(roles);
        return mapToResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + id));

        if (request.login() != null) {
            userRepository.findByLogin(request.login()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateResourceException("Пользователь с логином " + request.login() + " уже существует");
                }
            });
            user.setLogin(request.login());
        }

        if (request.email() != null) {
            userRepository.findByEmail(request.email()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new DuplicateResourceException("Пользователь с email " + request.email() + " уже существует");
                }
            });
            user.setEmail(request.email());
        }

        if (request.password() != null) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        if (request.enabled() != null) {
            user.setEnabled(request.enabled());
        }

        if (request.roleIds() != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : request.roleIds()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Роль не найдена с id: " + roleId));
                roles.add(role);
            }
            user.setRoles(roles);
        }
        return mapToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return mapToResponse(userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + id)));
    }

    @Override
    public UserResponse getUserByLogin(String login) {
        return mapToResponse(userRepository.findByLogin(login)
            .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с логином: " + login)));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return mapToResponse(userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с email: " + email)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Пользователь не найден с id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    private UserResponse mapToResponse(User u) {
        Set<UserResponse.RoleInfo> roleInfos = u.getRoles().stream()
            .map(r -> new UserResponse.RoleInfo(r.getId(), r.getName()))
            .collect(java.util.stream.Collectors.toSet());

        return new UserResponse(
                u.getId(),
                u.getLogin(),
                u.getEmail(),
                u.getEnabled(),
                roleInfos,
                u.getCreatedAt()
        );
    }
}
