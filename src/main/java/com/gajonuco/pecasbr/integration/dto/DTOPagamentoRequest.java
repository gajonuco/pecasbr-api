package com.gajonuco.pecasbr.integration.dto;

import com.gajonuco.pecasbr.model.Cliente;
import java.math.BigDecimal;

public record DTOPagamentoRequest(BigDecimal valorTotal, Cliente cliente, Integer idPedido) {}
