package com.example.demo.domain.user;


import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.feedback.Feedback;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String hashedPassword;

    @OneToMany(mappedBy = "user")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;



    public static User of( String email,
                           String name,
                           String salt,
                           String hashedPassword,
                           Role role) {
        return User.builder()
                .email(email)
                .name(name)
                .salt(salt)
                .hashedPassword(hashedPassword)
                .role(role)
                .build();
    }
}