package com.gabriel_nunez.oficina_mecanica.integration.dto;

// DTOClienteResponse.java
public record DTOClienteResponse(
    String id,
    String name,
    String cpfCnpj,
    String email
) {}