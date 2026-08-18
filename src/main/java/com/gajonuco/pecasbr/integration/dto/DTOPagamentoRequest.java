package com.gajonuco.pecasbr.integration.dto;

import com.gajonuco.pecasbr.model.Cliente;

public record DTOPagamentoRequest(Double valorTotal, Cliente cliente, Integer idPedido) {}
