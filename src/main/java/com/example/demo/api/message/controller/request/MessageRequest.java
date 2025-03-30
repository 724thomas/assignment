package com.example.demo.api.message.controller.request;

import lombok.Data;

@Data
public class MessageRequest {
    private Long chatId;
    private String model;
    private String content;
}
