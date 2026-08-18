package com.gajonuco.pecasbr.integration.dto;

public record DTOClienteRequest(
    String name,
    String cpfCnpj,
    String email,
    String mobilePhone,
    String addressNumber,
    String complement,
    String postalCode
) {}