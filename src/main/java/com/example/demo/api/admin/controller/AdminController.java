package com.example.demo.api.admin.controller;

import com.example.demo.api.admin.controller.request.FeedbackUpdateRequest;
import com.example.demo.api.admin.controller.response.ActivityReportResponse;
import com.example.demo.api.admin.service.AdminService;
import com.example.demo.api.chat.controller.response.ChatResponse;
import com.example.demo.api.feedback.controller.response.FeedbackResponse;
import com.example.demo.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @DeleteMapping("/chat")
    public DataResponse<ChatResponse> deleteChat(@RequestParam Long chatId) {
        return DataResponse.of(adminService.deleteChat(chatId));
    }

    @GetMapping("/feedback")
    public Page<FeedbackResponse> getFeedbackList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "all") String type
    ) {
        return adminService.getFeedbackList(page, size, sort, sortBy, type);
    }

    @PutMapping("/feedback")
    public DataResponse<FeedbackResponse> updateFeedback(@RequestBody FeedbackUpdateRequest request) {
        return DataResponse.of(adminService.updateFeedback(request));
    }

    @GetMapping("/activity")
    public DataResponse<ActivityReportResponse> getUserActivityReport() {
        return DataResponse.of(adminService.getUserActivityReport());
    }

    @GetMapping("/report/chat")
    public ResponseEntity<Resource> downloadChatReport() {
        return adminService.generateChatReportCsv();
    }


}
