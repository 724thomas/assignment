package com.example.demo.api.feedback.service;

import com.example.demo.api.feedback.controller.request.FeedbackRequest;
import com.example.demo.api.feedback.controller.response.FeedbackResponse;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.chat.api.ChatApiRepository;
import com.example.demo.domain.feedback.Feedback;
import com.example.demo.domain.feedback.api.FeedbackApiRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.api.UserApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final UserApiRepository userApiRepository;
    private final ChatApiRepository chatApiRepository;
    private final FeedbackApiRepository feedbackApiRepository;

    public FeedbackResponse createFeedback(Long userId, FeedbackRequest request) {
        User user = userApiRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Chat chat = chatApiRepository.findById(request.getChatId()).orElseThrow(() -> new IllegalArgumentException("Chat not found"));

        Feedback feedback = Feedback.of(user, chat, request.isPositive(), "PENDING");
        feedbackApiRepository.save(feedback);

        return FeedbackResponse.from(feedback);
    }

    public Page<FeedbackResponse> getFeedbackList(Long userId, Integer page, Integer size, String sort, String sortBy, String type) {
        User user = userApiRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sort), sortBy));

        if (type.equals("all")) {
            return feedbackApiRepository.findAllByUserId(userId, pageable).map(FeedbackResponse::from);
        } else if (type.equals("positive")) {
            return feedbackApiRepository.findAllByUserIdAndPositive(userId, true, pageable).map(FeedbackResponse::from);
        } else if (type.equals("negative")) {
            return feedbackApiRepository.findAllByUserIdAndPositive(userId, false, pageable).map(FeedbackResponse::from);
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
    }
}
