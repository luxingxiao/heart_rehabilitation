package com.heart.rehabilitation.config;

import com.heart.rehabilitation.model.Organization;
import com.heart.rehabilitation.model.OrganizationType;
import com.heart.rehabilitation.model.Role;
import com.heart.rehabilitation.model.RoleName;
import com.heart.rehabilitation.model.User;
import com.heart.rehabilitation.repository.OrganizationRepository;
import com.heart.rehabilitation.repository.RoleRepository;
import com.heart.rehabilitation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           OrganizationRepository organizationRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeSuperAdmin();
    }

    private void initializeRoles() {
        Arrays.stream(RoleName.values()).forEach(roleName -> {
            if (!roleRepository.findByName(roleName).isPresent()) {
                Role role = new Role(roleName);
                roleRepository.save(role);
            }
        });
    }

    private void initializeSuperAdmin() {
        if (organizationRepository.count() == 0) {
            Organization superOrg = new Organization();
            superOrg.setName("Super Admin Organization");
            superOrg.setType(OrganizationType.HOSPITAL);
            superOrg.setDescription("Default super admin organization");
            superOrg.setContactEmail("admin@heartrehabilitation.com");
            organizationRepository.save(superOrg);

            Role superAdminRole = roleRepository.findByName(RoleName.SUPER_ADMIN)
                    .orElseThrow(() -> new RuntimeException("SUPER_ADMIN role not found"));

            if (!userRepository.findByUsername("superadmin").isPresent()) {
                User superAdmin = new User();
                superAdmin.setUsername("superadmin");
                superAdmin.setEmail("superadmin@heartrehabilitation.com");
                superAdmin.setPassword(passwordEncoder.encode("admin123"));
                superAdmin.setFirstName("Super");
                superAdmin.setLastName("Admin");
                superAdmin.setOrganization(superOrg);
                superAdmin.setRoles(new HashSet<>(Arrays.asList(superAdminRole)));
                userRepository.save(superAdmin);
            }
        }
    }
}