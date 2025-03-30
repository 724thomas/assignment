package com.example.demo.dto;

import com.example.demo.domain.feedback.Feedback;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    private Long id;
    private Long userId;
    private Long chatId;
    private boolean isPositive;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static FeedbackDto from(Feedback feedback) {
        return FeedbackDto.builder()
                .id(feedback.getId())
                .userId(feedback.getUser().getId())
                .chatId(feedback.getChat().getId())
                .isPositive(feedback.isPositive())
                .status(feedback.getStatus())
                .createdAt(feedback.getCreatedAt())
                .modifiedAt(feedback.getModifiedAt())
                .build();
    }

    public static List<FeedbackDto> from(List<Feedback> feedbacks) {
        return feedbacks.stream().map(FeedbackDto::from).toList();
    }
}
