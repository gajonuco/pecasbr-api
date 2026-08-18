// DTOWebhookAsaas.java
package com.gabriel_nunez.oficina_mecanica.integration.dto;

public record DTOWebhookAsaas(
    String event,       // ex: "PAYMENT_CONFIRMED", "PAYMENT_RECEIVED"
    Payment payment
) {
    public record Payment(
        String id,      // paymentId do Asaas
        String status   // "CONFIRMED", "RECEIVED", etc.
    ) {}
}