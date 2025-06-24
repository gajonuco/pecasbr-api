package com.gabriel_nunez.oficina_mecanica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove o prefixo "Bearer "

                try {
                    Authentication authentication = tokenService.getAuthentication(token);
                    if (authentication != null) {
                        accessor.setUser(authentication); // Define o usuário autenticado no contexto STOMP
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao autenticar WebSocket: " + e.getMessage());
                    // Você pode lançar exceção aqui para rejeitar a conexão se necessário
                }
            }
        }

        return message;
    }
}
