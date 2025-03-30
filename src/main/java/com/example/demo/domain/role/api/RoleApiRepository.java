package com.example.demo.domain.role.api;

import com.example.demo.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleApiRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
