package com.gajonuco.pecasbr.service;

import org.springframework.stereotype.Component;

@Component
public interface IBotService {

    public boolean sendBotMessage(String message);
    
}
