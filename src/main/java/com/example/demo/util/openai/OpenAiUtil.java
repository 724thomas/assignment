package com.example.demo.util.openai;

import com.example.demo.dto.MessageDto;
import com.example.demo.util.openai.request.ChatCompletionRequest;
import com.example.demo.util.openai.request.ChatMessageDto;
import com.example.demo.util.openai.response.ChatCompletionResponse;
import com.example.demo.util.openai.response.ChatCompletionStreamResponse;
import com.example.demo.util.openai.response.ChoiceStreamDto;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenAiUtil {

    @Value("${api.openai.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .build();

    private static final String DEFAULT_MODEL = "gpt-4o";

    //todo: not working
    public Flux<String> chatWithStreaming(String model, String content, List<MessageDto> history) {
        List<ChatMessageDto> fewShots = new ArrayList<>(ChatMessageDto.from(history));
        fewShots.add(new ChatMessageDto("user", content));

        ChatCompletionRequest requestBody = new ChatCompletionRequest(
                (model != null) ? model : DEFAULT_MODEL,
                fewShots,
                0.0,
                true
        );
        return webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(ChatCompletionStreamResponse.class)
                .map(chunk -> {
                    List<ChoiceStreamDto> choices = chunk.getChoices();
                    if (choices == null || choices.isEmpty()) return null;

                    String deltaContent = choices.get(0).getDeltaDto().getContent();
                    return (deltaContent != null) ? deltaContent : null;
                })
                .filter(token -> token != null && !token.isBlank());
    }

    public String chatWithoutStreaming(String model, String content, List<MessageDto> history) {
        List<ChatMessageDto> fewShots = new ArrayList<>(ChatMessageDto.from(history));
        fewShots.add(new ChatMessageDto("user", content));

        ChatCompletionRequest requestBody = new ChatCompletionRequest(
                (model != null) ? model : DEFAULT_MODEL,
                fewShots,
                0.0,
                false
        );

        ChatCompletionResponse response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .block();

        return response.getChoices().get(0).getMessage().getContent();
    }
}
