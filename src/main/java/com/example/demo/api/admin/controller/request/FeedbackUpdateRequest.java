package com.example.demo.api.admin.controller.request;

import lombok.Data;

@Data
public class FeedbackUpdateRequest {
    Long feedbackId;
    String status;
}
