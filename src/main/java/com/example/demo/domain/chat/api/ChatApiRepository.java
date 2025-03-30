package com.example.demo.domain.chat.api;

import com.example.demo.domain.chat.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatApiRepository extends JpaRepository<Chat, Long> {
    Page<Chat> findAllByUserId(Long userId, Pageable pageable);
    List<Chat> findAllByCreatedAtAfter(LocalDateTime time);
}
