package com.gabriel_nunez.oficina_mecanica.service;

import org.springframework.stereotype.Component;

@Component
public interface IBotService {

    public boolean sendBotMessage(String message);
    
}
