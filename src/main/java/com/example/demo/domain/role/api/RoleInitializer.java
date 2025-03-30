package com.example.demo.domain.role.api;

import com.example.demo.domain.role.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleApiRepository roleApiRepository;

    @PostConstruct
    public void initRoles() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        roleApiRepository.findByName(roleName)
                .orElseGet(() -> roleApiRepository.save(Role.builder().name(roleName).build()));
    }
}
