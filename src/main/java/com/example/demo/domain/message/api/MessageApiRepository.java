package com.example.demo.domain.message.api;

import com.example.demo.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageApiRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(Long chatId);

    Page<Message> findAllByUserId(long userId, Pageable pageable);
}
