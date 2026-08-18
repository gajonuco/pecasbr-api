package com.gajonuco.pecasbr.service;

import com.google.firebase.messaging.*;
import com.gajonuco.pecasbr.model.Peca;
import com.gajonuco.pecasbr.model.Pedido;

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
                peca.getQuantidadeEstoque());

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
                peca.getQuantidadeEstoque());

        enviarNotificacao(titulo, mensagem, "CRITICO", peca.getId());
    }

    /**
     * Envia notificação de estoque zerado
     */
    public void notificarEstoqueZerado(Peca peca) {
        String titulo = "🔴 PRODUTO ESGOTADO";
        String mensagem = String.format(
                "O produto '%s' ficou SEM ESTOQUE e foi desabilitado!",
                peca.getNome());

        enviarNotificacao(titulo, mensagem, "ZERADO", peca.getId());
    }

    /**
     * Método genérico para enviar notificação
     * 🔥 IMPORTANTE: Envia apenas DATA, sem campo "notification"
     * Isso evita a notificação automática duplicada do Firebase
     */
    private void enviarNotificacao(String titulo, String corpo, String tipo, Integer idPeca) {
        String urlCompleta = frontendUrl + "/admin/main/editor-produto/" + idPeca;
        enviarNotificacaoComUrl(titulo, corpo, tipo, idPeca, urlCompleta);
    }

    /**
     * Envia notificação de pagamento confirmado (novo pedido criado)
     */
    public void notificarPagamentoConfirmado(Pedido pedido) {
        String titulo = "💰 Pagamento Confirmado";
        String mensagem = String.format(
                "Pedido #%d confirmado! Cliente realizou o pagamento com sucesso.",
                pedido.getId());

        enviarNotificacaoComUrl(titulo, mensagem, "PAGAMENTO", pedido.getId(),
                frontendUrl + "/admin/main/pedidos");
    }

    /**
     * Variante que aceita URL customizada — usada para notificações de pagamento
     */
    private void enviarNotificacaoComUrl(String titulo, String corpo, String tipo,
            Integer idReferencia, String urlCompleta) {
        try {
            System.out.println("📤 Enviando notificação de pagamento:");
            System.out.println("   Título: " + titulo);
            System.out.println("   Tipo: " + tipo);
            System.out.println("   URL: " + urlCompleta);

            Message message = Message.builder()
                    .putData("titulo", titulo)
                    .putData("corpo", corpo)
                    .putData("tipo", tipo)
                    .putData("idPeca", String.valueOf(idReferencia))
                    .putData("url", urlCompleta)
                    .putData("timestamp", String.valueOf(System.currentTimeMillis()))
                    .setTopic("estoque-alerts") // mesmo tópico já inscrito
                    .setWebpushConfig(WebpushConfig.builder()
                            .putHeader("Urgency", "high")
                            //.setFcmOptions(WebpushFcmOptions.builder()
                                    //.setLink(urlCompleta)
                                    //.build())
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notificação de pagamento enviada: " + response);

        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar notificação de pagamento:");
            e.printStackTrace();
        }
    }
}