package com.example.demo.api.chat.controller.request;

import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.feedback.Feedback;
import com.example.demo.domain.message.Message;
import com.example.demo.dto.FeedbackDto;
import com.example.demo.dto.MessageDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int messagesCount;
    private List<MessageDto> messages;
    private int feedBackCount;
    private List<FeedbackDto> feedbacks;

    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .userId(chat.getUser().getId())
                .initialContent(chat.getInitialContent())
                .createdAt(chat.getCreatedAt())
                .modifiedAt(chat.getModifiedAt())
                .messagesCount(chat.getMessages().size())
                .messages(MessageDto.from(chat.getMessages()))
                .feedBackCount(chat.getFeedbacks().size())
                .feedbacks(FeedbackDto.from(chat.getFeedbacks()))
                .build();
    }
}
