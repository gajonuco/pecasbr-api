// DTOWebhookAsaas.java
package com.gajonuco.pecasbr.integration.dto;

public record DTOWebhookAsaas(
    String event,       // ex: "PAYMENT_CONFIRMED", "PAYMENT_RECEIVED"
    Payment payment
) {
    public record Payment(
        String id,      // paymentId do Asaas
        String status   // "CONFIRMED", "RECEIVED", etc.
    ) {}
}