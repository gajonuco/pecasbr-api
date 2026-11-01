package com.gajonuco.pecasbr.config;

import com.gajonuco.pecasbr.service.CatalogoTools;
import com.gajonuco.pecasbr.service.GeminiAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GeminiThinkingConfig;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GeminiConfig {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.model-name}")
    private String modelName;

    @Bean
    public ChatModel geminiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.7)
                .maxOutputTokens(1024)
                .timeout(Duration.ofSeconds(30))
                .returnThinking(true)
                .sendThinking(true)
                .logRequestsAndResponses(true) // nunca logar prompts/respostas com dados do cliente
                .build();
    }

    @Bean
    public GeminiAssistant geminiAssistant(ChatModel geminiChatModel, CatalogoTools catalogoTools) {
        return dev.langchain4j.service.AiServices.builder(GeminiAssistant.class)
                .chatModel(geminiChatModel)
                .tools(catalogoTools)
                .toolArgumentsErrorHandler(dev.langchain4j.service.tool.ToolArgumentsErrorHandler.failInvocation())
                .toolExecutionErrorHandler(dev.langchain4j.service.tool.ToolExecutionErrorHandler.sendExceptionMessageToLlm())
                .build();
    }
}