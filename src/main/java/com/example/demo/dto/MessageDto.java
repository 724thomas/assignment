package com.example.demo.dto;

import com.example.demo.domain.message.Message;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private Long userId;
    private Long chatId;
    private String content;
    private String response;
    private String model;
    private String createdAt;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .userId(message.getUser().getId())
                .chatId(message.getChat().getId())
                .content(message.getContent())
                .response(message.getResponse())
                .model(message.getModel())
                .build();
    }

    public static List<MessageDto> from(List<Message> messages) {
        return messages.stream()
                .map(MessageDto::from)
                .toList();
    }
}