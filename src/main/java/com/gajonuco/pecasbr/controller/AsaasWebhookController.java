package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dao.PedidoDAO;
import com.gajonuco.pecasbr.integration.dto.DTOWebhookAsaas;
import com.gajonuco.pecasbr.model.Pedido;
import com.gajonuco.pecasbr.service.NotificationService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;
import org.slf4j.Logger;

@RestController
@RequestMapping("/webhook")
public class AsaasWebhookController {

    private static final Logger log = LoggerFactory.getLogger(AsaasWebhookController.class);

    private final PedidoDAO pedidoDAO;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final String webhookToken;

    public AsaasWebhookController(
            PedidoDAO pedidoDAO,
            SimpMessagingTemplate messagingTemplate,
            NotificationService notificationService,
            @Value("${asaas.webhook.token}") String webhookToken) {
        this.notificationService = notificationService;
        this.pedidoDAO = pedidoDAO;
        this.messagingTemplate = messagingTemplate;
        this.webhookToken = webhookToken;
    }

    @PostMapping("/asaas")
    public ResponseEntity<Void> receberEvento(
            @RequestHeader(value = "asaas-access-token", required = false) String tokenRecebido,
            @RequestBody DTOWebhookAsaas dto) {

        if(!tokenValido(tokenRecebido)){
            log.warn("[Webhook] Requisição rejeitada: token asaas-access-token ausente ou inválido.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Retorna 200 imediatamente para eventos sem payload relevante
        if (dto.event() == null || dto.payment() == null) {
            return ResponseEntity.ok().build();
        }

        boolean isPago = dto.event().equals("PAYMENT_CONFIRMED")
                || dto.event().equals("PAYMENT_RECEIVED");

        if (!isPago) {
            log.info("[Webhook] Evento ignorado: {}", dto.event());
            return ResponseEntity.ok().build();
        }

        String paymentId = dto.payment().id();
        Optional<Pedido> pedidoOpt = pedidoDAO.findByAsaasPaymentId(paymentId);

        if (pedidoOpt.isEmpty()) {
            // Loga para debug mas retorna 200 — Asaas não deve retentar
            System.out.println("[Webhook] Pedido não encontrado para paymentId: " + paymentId);
            return ResponseEntity.ok().build();
        }

        Pedido pedido = pedidoOpt.get();

        // Evita reprocessar pedido já confirmado
        if (pedido.getStatus() == Pedido.PAGO) {
            log.info("[Webhook] Pedido #{} já estava confirmado, ignorado", pedido.getId());
            return ResponseEntity.ok().build();
        }

        pedido.setStatus(Pedido.PAGO);
        pedidoDAO.save(pedido);
        log.info("[Webhook] Pedido #{} atualizado para PAGO.", pedido.getId());

        // 🔔 Notifica administradores via Firebase
        notificationService.notificarPagamentoConfirmado(pedido);

        // WebSocket para a tela de recibo
        messagingTemplate.convertAndSend("/topic/payment/" + pedido.getId(), pedido.getId());

        return ResponseEntity.ok().build();

    }

    private boolean tokenValido(String tokenRecebido) {
        if (tokenRecebido == null || webhookToken == null) {
            return false;
        }

        return MessageDigest.isEqual(
                tokenRecebido.getBytes(StandardCharsets.UTF_8),
                webhookToken.getBytes(StandardCharsets.UTF_8)
        );
    }
}