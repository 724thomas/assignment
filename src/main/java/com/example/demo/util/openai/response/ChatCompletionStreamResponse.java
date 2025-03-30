package com.example.demo.util.openai.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionStreamResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChoiceStreamDto> choices;
}
