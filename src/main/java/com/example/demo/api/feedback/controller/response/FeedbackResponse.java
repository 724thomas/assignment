package com.example.demo.api.feedback.controller.response;

import com.example.demo.domain.feedback.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackResponse {
    private Long id;
    private Long userId;
    private Long chatId;
    private boolean isPositive;
    private String status;

    public static FeedbackResponse from(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .userId(feedback.getUser().getId())
                .chatId(feedback.getChat().getId())
                .isPositive(feedback.isPositive())
                .status(feedback.getStatus())
                .build();
    }
}
