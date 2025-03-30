package com.example.demo.api.feedback.controller;

import com.example.demo.api.feedback.controller.request.FeedbackRequest;
import com.example.demo.api.feedback.controller.response.FeedbackResponse;
import com.example.demo.api.feedback.service.FeedbackService;
import com.example.demo.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping()
    public DataResponse<FeedbackResponse> createFeedback(
            @AuthenticationPrincipal Long userId,
            @RequestBody FeedbackRequest request
            ) {
        return DataResponse.of(feedbackService.createFeedback(userId, request));
    }

    @GetMapping()
    public Page<FeedbackResponse> getFeedbackList(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "all") String type
            ) {
        return feedbackService.getFeedbackList(userId, page, size, sort, sortBy, type);
    }
}
