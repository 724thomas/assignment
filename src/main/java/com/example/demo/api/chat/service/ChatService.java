package com.example.demo.api.chat.service;

import com.example.demo.api.chat.controller.response.ChatResponse;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.chat.api.ChatApiRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.api.UserApiRepository;
import com.example.demo.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserApiRepository userApiRepository;
    private final ChatApiRepository chatApiRepository;

    public Page<ChatResponse> getChatList(Long userId, int page, int size, String sort) {
        User user = userApiRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find User"));

        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));

        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            return chatApiRepository.findAll(pageable)
                    .map(ChatResponse::from);
        }

        return chatApiRepository.findAllByUserId(userId, pageable)
                .map(ChatResponse::from);
    }

    public ChatResponse deleteChat(Long userId, Long chatId) {
        User user = userApiRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find User"));

        Chat chat = chatApiRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Chat"));

        if (!chat.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Access Denied");
        }

        chatApiRepository.delete(chat);
        return ChatResponse.from(chat);
    }
}
