package com.example.demo.api.chat.controller.response;

import com.example.demo.domain.chat.Chat;
import com.example.demo.dto.FeedbackDto;
import com.example.demo.dto.MessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class ChatResponse {
    private Long id;
    private Long userId;
    private String initialContent;
    private List<MessageDto> messages;
    private List<FeedbackDto> feedbacks;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .userId(chat.getUser().getId())
                .initialContent(chat.getInitialContent())
                .messages(MessageDto.from(chat.getMessages()))
                .feedbacks(FeedbackDto.from(chat.getFeedbacks()))
                .createdAt(chat.getCreatedAt())
                .modifiedAt(chat.getModifiedAt())
                .build();
    }
}
