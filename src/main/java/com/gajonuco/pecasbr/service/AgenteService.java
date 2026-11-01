package com.gajonuco.pecasbr.service;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class AgenteService {

    private  final GeminiAssistant geminiAssistant;

    public AgenteService(GeminiAssistant geminiAssistant)
    {
        this.geminiAssistant = geminiAssistant;
    }

    public String responder(String mensagem) {

        return geminiAssistant.chat(mensagem);
    }
}