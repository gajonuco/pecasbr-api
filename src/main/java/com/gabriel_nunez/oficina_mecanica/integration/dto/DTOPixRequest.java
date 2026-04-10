package com.gabriel_nunez.oficina_mecanica.integration.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

// DTOPixRequest.java
public record DTOPixRequest(
    String billingType,
    String customer,

    Double value,
        @JsonFormat(pattern = "yyyy-MM-dd")  // ← garante o formato esperado pelo Asaas

    LocalDate dueDate,
    String description,
    Callback callback
) {
    public record Callback(String successUrl, Boolean autoRedirect) {}
}