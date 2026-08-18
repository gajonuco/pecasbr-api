package com.gabriel_nunez.oficina_mecanica.integration.dto;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public record DTOPagamentoRequest(Double valorTotal, Cliente cliente, Integer idPedido) {}
