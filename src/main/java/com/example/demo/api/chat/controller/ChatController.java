package com.example.demo.api.chat.controller;

import com.example.demo.api.chat.controller.response.ChatResponse;
import com.example.demo.api.chat.service.ChatService;
import com.example.demo.domain.chat.Chat;
import com.example.demo.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping()
    public DataResponse<Page<ChatResponse>> getChatList(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort
            ) {
        return DataResponse.of(chatService.getChatList(userId, page, size, sort));
    }

    @DeleteMapping()
    public DataResponse<ChatResponse> deleteChat(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long chatId
    ) {
        return DataResponse.of(chatService.deleteChat(userId, chatId));
    }
}
