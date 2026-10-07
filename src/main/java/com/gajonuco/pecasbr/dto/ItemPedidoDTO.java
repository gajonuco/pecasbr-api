package com.gajonuco.pecasbr.dto;

import jakarta.validation.constraints.Min;

public record ItemPedidoDTO(
        int idPeca,
        @Min(1) int quantidade,
        Integer idVariacao,
        String corEscolhida,
        String tamanhoEscolhido) {
}