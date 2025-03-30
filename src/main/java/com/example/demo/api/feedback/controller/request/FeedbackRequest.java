package com.example.demo.api.feedback.controller.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private Long chatId;
    private boolean isPositive;
}
