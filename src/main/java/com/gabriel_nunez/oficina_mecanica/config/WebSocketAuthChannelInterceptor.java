package com.gabriel_nunez.oficina_mecanica.config;

import com.gabriel_nunez.oficina_mecanica.repository.ClienteUsuarioRepository;
import com.gabriel_nunez.oficina_mecanica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClienteUsuarioRepository clienteUsuarioRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
                String username = tokenService.validateToken(token);

                if (username != null && !username.isEmpty()) {
                    // Tenta encontrar um User
                    UserDetails user = userRepository.findByLogin(username);

                    // Se não encontrou, tenta como ClienteUsuario
                    if (user == null) {
                        user = clienteUsuarioRepository.findByLogin(username).orElse(null);
                    }

                    if (user != null) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities());
                        accessor.setUser(auth);
                    }
                }
            }
        }

        return message;
    }
}
