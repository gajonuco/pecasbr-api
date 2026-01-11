package com.gabriel_nunez.oficina_mecanica.service;

import com.google.firebase.messaging.*;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Injeta a URL base do application.properties
    @Value("${app.frontend.url}")
    private String frontendUrl;

    /**
     * Envia notificação de estoque baixo
     */
    public void notificarEstoqueBaixo(Peca peca) {
        String titulo = "⚠️ Estoque Baixo";
        String mensagem = String.format(
            "O produto '%s' está com estoque baixo (%d unidades). Reabasteça em breve!",
            peca.getNome(),
            peca.getQuantidadeEstoque()
        );
        
        enviarNotificacao(titulo, mensagem, "BAIXO", peca.getId());
    }

    /**
     * Envia notificação de estoque crítico
     */
    public void notificarEstoqueCritico(Peca peca) {
        String titulo = "🚨 ESTOQUE CRÍTICO";
        String mensagem = String.format(
            "URGENTE: '%s' está quase esgotado! Apenas %d unidades restantes.",
            peca.getNome(),
            peca.getQuantidadeEstoque()
        );
        
        enviarNotificacao(titulo, mensagem, "CRITICO", peca.getId());
    }

    /**
     * Envia notificação de estoque zerado
     */
    public void notificarEstoqueZerado(Peca peca) {
        String titulo = "🔴 PRODUTO ESGOTADO";
        String mensagem = String.format(
            "O produto '%s' ficou SEM ESTOQUE e foi desabilitado!",
            peca.getNome()
        );
        
        enviarNotificacao(titulo, mensagem, "ZERADO", peca.getId());
    }

    /**
     * Método genérico para enviar notificação
     * 🔥 IMPORTANTE: Envia apenas DATA, sem campo "notification"
     * Isso evita a notificação automática duplicada do Firebase
     */
    private void enviarNotificacao(String titulo, String corpo, String tipo, Integer idPeca) {
        try {
            String urlCompleta = frontendUrl + "/admin/main/editor-produto/" + idPeca;
            
            System.out.println("📤 Enviando notificação:");
            System.out.println("   Título: " + titulo);
            System.out.println("   Tipo: " + tipo);
            System.out.println("   URL: " + urlCompleta);

            // 🔥 CORREÇÃO: Envia APENAS "data", SEM campo "notification"
            // Isso garante que apenas o Service Worker mostre a notificação customizada
            Message message = Message.builder()
                // ❌ REMOVIDO: .setNotification() - causava duplicação
                .putData("titulo", titulo)        // 🔥 Título vai em "data"
                .putData("corpo", corpo)          // 🔥 Corpo vai em "data"
                .putData("tipo", tipo)
                .putData("idPeca", String.valueOf(idPeca))
                .putData("url", urlCompleta)
                .putData("timestamp", String.valueOf(System.currentTimeMillis()))
                .setTopic("estoque-alerts")
                .setWebpushConfig(WebpushConfig.builder()
                    // Configurações adicionais para web
                    .putHeader("Urgency", tipo.equals("CRITICO") ? "high" : "normal")
                    .setFcmOptions(WebpushFcmOptions.builder()
                        .setLink(urlCompleta)
                        .build())
                    .build())
                .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notificação enviada com sucesso: " + response);
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar notificação:");
            e.printStackTrace();
        }
    }
}