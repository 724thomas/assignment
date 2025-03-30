package com.example.demo.domain.feedback;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseEntity {
    /*
    - 사용자 ID(string)
    - 대화 ID(string): 피드백 대인 대화의 ID
    - 긍정/부정 유무(boolean)
    - 생성일시(timestampz)
    - 상태(string): `pending`, `resolved` 중 하나의 상태를 가집니다.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(nullable = false)
    private boolean isPositive;

    @Column(nullable = false)
    private String status;

    public static Feedback of(User user, Chat chat, boolean isPositive, String status) {
        return Feedback.builder()
                .user(user)
                .chat(chat)
                .isPositive(isPositive)
                .status(status)
                .build();
    }
}
