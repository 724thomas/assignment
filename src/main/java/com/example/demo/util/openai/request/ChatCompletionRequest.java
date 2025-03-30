package com.example.demo.util.openai.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest {
    private String model;
    private List<ChatMessageDto> messages;
    private Double temperature;
    private boolean stream;
}
