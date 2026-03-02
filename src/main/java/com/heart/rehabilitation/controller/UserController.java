package com.heart.rehabilitation.controller;

import com.heart.rehabilitation.dto.UserResponse;
import com.heart.rehabilitation.dto.UserUpdateRequest;
import com.heart.rehabilitation.model.User;
import com.heart.rehabilitation.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        User user = userService.getCurrentUserProfile();
        return ResponseEntity.ok(toUserResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@Valid @RequestBody UserUpdateRequest updateRequest) {
        User updatedUser = new User();
        updatedUser.setFirstName(updateRequest.getFirstName());
        updatedUser.setLastName(updateRequest.getLastName());
        updatedUser.setPhoneNumber(updateRequest.getPhoneNumber());
        User savedUser = userService.updateCurrentUser(updatedUser);
        return ResponseEntity.ok(toUserResponse(savedUser));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsersInCurrentOrganization() {
        List<User> users = userService.getUsersInCurrentOrganization();
        List<UserResponse> responses = users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or (hasRole('HOSPITAL_ADMIN') and @currentUserService.getCurrentUserOrganizationId() == @userRepository.findById(#id).orElseThrow().organization.id)")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(toUserResponse(user));
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getOrganization(),
                user.getRoles(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}