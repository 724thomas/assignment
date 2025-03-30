package com.example.demo.api.message.controller;


import com.example.demo.api.message.controller.request.MessageRequest;
import com.example.demo.api.message.controller.response.MessageResponse;
import com.example.demo.api.message.service.MessageService;
import com.example.demo.dto.MessageDto;
import com.example.demo.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageService messageService;


    @PostMapping()
    public DataResponse<MessageResponse> createMessage(@AuthenticationPrincipal Long userId,
                                                       @RequestBody MessageRequest request) {
        userId = 1L;
        return DataResponse.of(messageService.createMessage(userId, request));
    }

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamMessages(@AuthenticationPrincipal Long userId,
                                       @RequestBody MessageRequest request) {
        return messageService.createMessageStream(userId, request);
    }

    @GetMapping()
    public DataResponse<Page<MessageResponse>> getMessages(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return DataResponse.of(messageService.getMessages(userId, pageable));
    }

}
