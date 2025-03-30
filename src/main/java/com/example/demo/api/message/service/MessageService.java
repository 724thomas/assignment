package com.example.demo.api.message.service;


import com.example.demo.api.message.controller.request.MessageRequest;
import com.example.demo.api.message.controller.response.MessageResponse;
import com.example.demo.domain.chat.Chat;
import com.example.demo.domain.chat.api.ChatApiRepository;
import com.example.demo.domain.message.Message;
import com.example.demo.domain.message.api.MessageApiRepository;
import com.example.demo.domain.report.event.ReportEvent;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.api.UserApiRepository;
import com.example.demo.dto.MessageDto;
import com.example.demo.util.openai.OpenAiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final OpenAiUtil openAiUtil;

    private final UserApiRepository userRepository;
    private final ChatApiRepository chatApiRepository;
    private final MessageApiRepository messageApiRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Transactional
    public MessageResponse createMessage(Long userId, MessageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find User."));


        //채팅 존재 여부 & 유효성 확인
        boolean isNewChat = false;
        Chat chat = chatApiRepository.findById(request.getChatId())
                .filter(this::isChatValid)
                .orElse(null);

        if (chat == null) {
            chat = createChat(user, request);
            isNewChat = true;
        }
        chat.setModifiedAt(LocalDateTime.now());
        chatApiRepository.save(chat);

        if (isNewChat) {
            applicationEventPublisher.publishEvent(ReportEvent.of("CreateChat", chat.getId()));
        }

        // 과거 메시지 불러오기 (few shot)
        List<Message> messages = messageApiRepository.findAllByChatId(chat.getId());
        List<MessageDto> sortedMessages = MessageDto.from(messages);

        // OpenAi API 호출
        String model = request.getModel();
        String content = request.getContent();
        String response = openAiUtil.chatWithoutStreaming(model, content, sortedMessages);

        // 결과 저장
        Message message = Message.of(chat, user, model, content, response);
        messageApiRepository.save(message);

        return MessageResponse.from(message);
    }

    @Transactional
    public Flux<String> createMessageStream(Long userId, MessageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        //채팅 존재 여부 & 유효성 확인
        boolean isNewChat = false;
        Chat chat = chatApiRepository.findById(request.getChatId())
                .filter(this::isChatValid)
                .orElse(null);

        if (chat == null) {
            chat = createChat(user, request);
            isNewChat = true;
        }
        chat.setModifiedAt(LocalDateTime.now());
        chatApiRepository.save(chat);

        if (isNewChat) {
            applicationEventPublisher.publishEvent(ReportEvent.of("CreateChat", chat.getId()));
        }

        // 과거 메시지 불러오기 (few shot)
        List<Message> messages = messageApiRepository.findAllByChatId(chat.getId());
        List<MessageDto> sortedMessages = MessageDto.from(messages);

        Flux<String> stream = openAiUtil.chatWithStreaming(request.getModel(), request.getContent(), sortedMessages);
        return stream;
    }



    @Transactional(readOnly = true)
    public Page<MessageResponse> getMessages(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Role role = user.getRole();

        if (role.getName().equals("ROLE_ADMIN")) {
            return messageApiRepository.findAll(pageable)
                    .map(MessageResponse::from);
        }
        return messageApiRepository.findAllByUserId(userId, pageable)
                .map(MessageResponse::from);
    }


    // 채팅 유효성(30분) 확인
    private boolean isChatValid(Chat chat) {
        return chat.getModifiedAt() != null &&
                chat.getModifiedAt().isAfter(LocalDateTime.now().minusMinutes(30));
    }

    // 채팅 생성
    private Chat createChat(User user, MessageRequest request) {
        return Chat.of(user, request.getContent());
    }




}
