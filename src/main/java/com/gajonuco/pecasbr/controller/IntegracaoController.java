/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.controller.IntegracaoController
 *  com.gajonuco.pecasbr.dto.DTOConfirmation
 *  com.gajonuco.pecasbr.integration.dto.DTOPagamentoRequest
 *  com.gajonuco.pecasbr.integration.dto.DTOResponse
 *  com.gajonuco.pecasbr.integration.service.IAsaasService
 *  org.springframework.http.ResponseEntity
 *  org.springframework.messaging.simp.SimpMessagingTemplate
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.DTOConfirmation;
import com.gajonuco.pecasbr.integration.dto.DTOPagamentoRequest;
import com.gajonuco.pecasbr.integration.dto.DTOResponse;
import com.gajonuco.pecasbr.integration.service.IAsaasService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegracaoController {
    private final IAsaasService service;
    private final SimpMessagingTemplate messagingTemplate;

    public IntegracaoController(IAsaasService service, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping(value={"/createPayment"})
    public ResponseEntity<?> createPayment(@RequestBody DTOPagamentoRequest dto) {
        DTOResponse response = this.service.createPaymentLink(dto.valorTotal(), dto.cliente(), dto.idPedido());
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value={"/webhook"})
    public ResponseEntity<?> receivePayment(@RequestBody DTOConfirmation confirmation) {
        System.out.println("=== WEBHOOK RECEBIDO ===");
        System.out.println("Evento: " + confirmation.getEvent());
        System.out.println("ID: " + confirmation.getId());
        if (confirmation != null && ("PAYMENT_RECEIVED".equals(confirmation.getEvent()) || "PAYMENT_CONFIRMED".equals(confirmation.getEvent()))) {
            System.out.println(">>> Disparando WebSocket para o front-end");
            this.messagingTemplate.convertAndSend("/topic/payment", confirmation);
            return ResponseEntity.ok(confirmation);
        }
        System.out.println("Evento ignorado (n\u00e3o \u00e9 confirma\u00e7\u00e3o de pagamento): " + confirmation.getEvent());
        return ResponseEntity.ok().build();
    }
}

