package com.example.demo.util.openai.response;

import com.example.demo.util.openai.request.ChatMessageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDto {
    private ChatMessageDto message;
}
