package com.example.demo.domain.chat;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.feedback.Feedback;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat extends BaseEntity {
    /*
    - 질문(string)
    - 답변(string)
    - 생성일시(timestampz)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String initialContent;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    private List<Feedback> feedbacks = new ArrayList<>();

    public static Chat of(User user, String initialContent) {
        return Chat.builder()
                .user(user)
                .initialContent(initialContent)
                .build();
    }
}
