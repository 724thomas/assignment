package com.example.demo.util.openai.request;

import com.example.demo.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private String role;
    private String content;

    public static List<ChatMessageDto> from(MessageDto messageDto) {
        List<ChatMessageDto> chatMessageDtoList = new ArrayList<>();
        chatMessageDtoList.add(new ChatMessageDto("user", messageDto.getContent()));
        chatMessageDtoList.add(new ChatMessageDto("assistant", messageDto.getResponse()));
        return chatMessageDtoList;
    }

    public static List<ChatMessageDto> from(List<MessageDto> messageDtoList) {
        List<ChatMessageDto> chatMessageDtoList = new ArrayList<>();
        for (MessageDto messageDto : messageDtoList) {
            chatMessageDtoList.add(new ChatMessageDto("user", messageDto.getContent()));
            chatMessageDtoList.add(new ChatMessageDto("assistant", messageDto.getResponse()));
        }
        return chatMessageDtoList;
    }
}
