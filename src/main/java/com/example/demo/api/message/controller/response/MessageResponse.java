package com.example.demo.api.message.controller.response;

import com.example.demo.domain.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private Long userId;
    private Long chatId;
    private String content;
    private String response;
    private String model;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .userId(message.getUser().getId())
                .chatId(message.getChat().getId())
                .content(message.getContent())
                .response(message.getResponse())
                .model(message.getModel())
                .createdAt(message.getCreatedAt())
                .modifiedAt(message.getModifiedAt())
                .build();
    }
}
