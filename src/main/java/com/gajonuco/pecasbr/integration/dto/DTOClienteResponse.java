package com.gajonuco.pecasbr.integration.dto;

// DTOClienteResponse.java
public record DTOClienteResponse(
    String id,
    String name,
    String cpfCnpj,
    String email
) {}