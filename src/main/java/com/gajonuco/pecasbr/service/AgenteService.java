package com.gajonuco.pecasbr.service;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class AgenteService {

    private final ChatModel geminiChatModel;

    public AgenteService(ChatModel geminiChatModel) {
        this.geminiChatModel = geminiChatModel;
    }

    public String responder(String mensagem) {
        return geminiChatModel.chat(mensagem);
    }
}