package com.example.demo.api.admin.service;


import com.example.demo.api.admin.controller.request.FeedbackUpdateRequest;
import com.example.demo.api.admin.controller.response.ActivityReportResponse;
import com.example.demo.api.chat.controller.response.ChatResponse;
import com.example.demo.api.feedback.controller.response.FeedbackResponse;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.chat.api.ChatApiRepository;
import com.example.demo.domain.feedback.Feedback;
import com.example.demo.domain.feedback.api.FeedbackApiRepository;
import com.example.demo.domain.report.api.ReportApiRepository;
import com.example.demo.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ChatApiRepository chatApiRepository;
    private final FeedbackApiRepository feedbackApiRepository;
    private final ReportApiRepository reportApiRepository;

    public ChatResponse deleteChat(Long chatId) {
        Chat chat = chatApiRepository.findById(chatId).orElseThrow(() -> new BadRequestException("Chat does not exist"));
        chatApiRepository.delete(chat);
        return ChatResponse.from(chat);
    }

    public Page<FeedbackResponse> getFeedbackList(Integer page, Integer size, String sort, String sortBy, String type) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sort), sortBy));

        if (type.equals("all")) {
            return feedbackApiRepository.findAll(pageable).map(FeedbackResponse::from);
        } else if (type.equals("positive")) {
            return feedbackApiRepository.findAllByPositive(true, pageable).map(FeedbackResponse::from);
        } else if (type.equals("negative")) {
            return feedbackApiRepository.findAllByPositive(false, pageable).map(FeedbackResponse::from);
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
    }

    public FeedbackResponse updateFeedback(FeedbackUpdateRequest request) {
        Feedback feedback = feedbackApiRepository.findById(request.getFeedbackId()).orElseThrow(() -> new BadRequestException("Feedback does not exist"));
        feedback.setStatus(request.getStatus());
        feedbackApiRepository.save(feedback);
        return FeedbackResponse.from(feedback);
    }

    public ActivityReportResponse getUserActivityReport() {
        LocalDateTime since = LocalDateTime.now().minusDays(1);
        int signUpCount = reportApiRepository.countByTypeAndCreatedAtAfter("SignUp", since);
        int loginCount = reportApiRepository.countByTypeAndCreatedAtAfter("Login", since);
        int chatCount = reportApiRepository.countByTypeAndCreatedAtAfter("CreateChat", since);

        return ActivityReportResponse.builder()
                .signUpCount(signUpCount)
                .loginCount(loginCount)
                .chatCreatedCount(chatCount)
                .build();
    }

    public ResponseEntity<Resource> generateChatReportCsv() {
        LocalDateTime since = LocalDateTime.now().minusDays(1);
        List<Chat> chats = chatApiRepository.findAllByCreatedAtAfter(since);

        StringWriter writer = new StringWriter();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Chat ID", "User ID", "Username", "Initial Content", "Created At"))) {
            for (Chat chat : chats) {
                csvPrinter.printRecord(
                        chat.getId(),
                        chat.getUser().getId(),
                        chat.getUser().getName(),
                        chat.getInitialContent(),
                        chat.getCreatedAt()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("failed generating CSV", e);
        }

        ByteArrayResource resource = new ByteArrayResource(writer.toString().getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=chat-report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
