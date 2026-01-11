package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.NotificationSubscribeDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*") // Permite requisições do Angular
public class NotificationController {

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody NotificationSubscribeDTO dto) {
        try {
            System.out.println("📨 Recebendo inscrição no tópico...");
            System.out.println("Token: " + dto.getToken().substring(0, 20) + "...");
            System.out.println("Tópico: " + dto.getTopic());

            // ⚠️ CORREÇÃO: Método correto para inscrever um token em um tópico
            FirebaseMessaging.getInstance()
                .subscribeToTopic(
                    Collections.singletonList(dto.getToken()), // Lista com 1 token
                    dto.getTopic()
                );

            System.out.println("✅ Token inscrito com sucesso no tópico: " + dto.getTopic());
            
            return ResponseEntity.ok()
                .body(Collections.singletonMap("message", "Inscrito com sucesso"));

        } catch (FirebaseMessagingException e) {
            System.err.println("❌ Erro do Firebase ao inscrever:");
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Collections.singletonMap("error", e.getMessage()));
                
        } catch (Exception e) {
            System.err.println("❌ Erro geral ao inscrever:");
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Collections.singletonMap("error", "Erro interno"));
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestBody NotificationSubscribeDTO dto) {
        try {
            FirebaseMessaging.getInstance()
                .unsubscribeFromTopic(
                    Collections.singletonList(dto.getToken()),
                    dto.getTopic()
                );

            System.out.println("✅ Token desinscrito do tópico: " + dto.getTopic());
            
            return ResponseEntity.ok()
                .body(Collections.singletonMap("message", "Desinscrito com sucesso"));

        } catch (Exception e) {
            System.err.println("❌ Erro ao desinscrever:");
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}