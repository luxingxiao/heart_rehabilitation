package com.heart.rehabilitation.service;

import com.heart.rehabilitation.model.User;
import com.heart.rehabilitation.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public UserService(UserRepository userRepository, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    public User getCurrentUserProfile() {
        return currentUserService.getCurrentUser();
    }

    @Transactional
    public User updateCurrentUser(User updatedUser) {
        User currentUser = currentUserService.getCurrentUser();
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
        // Do not allow updating username, email, password, organization, roles via this method
        return userRepository.save(currentUser);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public List<User> getUsersInCurrentOrganization() {
        Long orgId = currentUserService.getCurrentUserOrganizationId();
        return userRepository.findByOrganizationId(orgId);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or (hasRole('HOSPITAL_ADMIN') and @currentUserService.getCurrentUserOrganizationId() == #userId.organization.id) ...")
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}