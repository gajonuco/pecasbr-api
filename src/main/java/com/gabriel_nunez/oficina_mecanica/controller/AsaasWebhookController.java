package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dao.PedidoDAO;
import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOWebhookAsaas;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import com.gabriel_nunez.oficina_mecanica.service.NotificationService;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/webhook")
public class AsaasWebhookController {

    private final PedidoDAO pedidoDAO;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public AsaasWebhookController(PedidoDAO pedidoDAO, SimpMessagingTemplate messagingTemplate,
            NotificationService notificationService) {
        this.notificationService = notificationService;
        this.pedidoDAO = pedidoDAO;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/asaas")
    public ResponseEntity<Void> receberEvento(@RequestBody DTOWebhookAsaas dto) {

        // Retorna 200 imediatamente para eventos sem payload relevante
        if (dto.event() == null || dto.payment() == null) {
            return ResponseEntity.ok().build();
        }

        boolean isPago = dto.event().equals("PAYMENT_CONFIRMED")
                || dto.event().equals("PAYMENT_RECEIVED");

        if (!isPago) {
            System.out.println("[Webhook] Evento ignorado: " + dto.event());
            return ResponseEntity.ok().build();
        }

        String paymentId = dto.payment().id();
        System.out.println("[Webhook] Pagamento confirmado para paymentId: " + paymentId);

        Optional<Pedido> pedidoOpt = pedidoDAO.findByAsaasPaymentId(paymentId);

        if (pedidoOpt.isEmpty()) {
            // Loga para debug mas retorna 200 — Asaas não deve retentar
            System.out.println("[Webhook] Pedido não encontrado para paymentId: " + paymentId);
            return ResponseEntity.ok().build();
        }

        Pedido pedido = pedidoOpt.get();

        // Evita reprocessar pedido já confirmado
        if (pedido.getStatus() == Pedido.PAGO) {
            System.out.println("[Webhook] Pedido #" + pedido.getId() + " já estava confirmado, ignorando.");
            return ResponseEntity.ok().build();
        }

        pedido.setStatus(Pedido.PAGO);
        pedidoDAO.save(pedido);

        System.out.println("[Webhook] Pedido #" + pedido.getId() + " atualizado para PAGO.");

        // 🔔 Notifica administradores via Firebase
        notificationService.notificarPagamentoConfirmado(pedido);

        // WebSocket para a tela de recibo
        messagingTemplate.convertAndSend("/topic/payment/" + pedido.getId(), pedido.getId());

        return ResponseEntity.ok().build();
    }
}