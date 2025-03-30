package com.example.demo.domain.user.api;


import com.example.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserApiRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    int countByCreatedAtAfter(LocalDateTime time);
}
