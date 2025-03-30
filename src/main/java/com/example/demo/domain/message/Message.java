package com.example.demo.domain.message;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;
    private String response;
    private String model;

    public static Message of(Chat chat, User user, String model, String content, String response) {
        return Message.builder()
                .chat(chat)
                .user(user)
                .model(model)
                .content(content)
                .response(response)
                .build();
    }
}
