package com.gajonuco.pecasbr.dto;

public record AcaoSugerida(
        TipoAcaoAgente tipo,
        Integer pecaId,
        Integer quantidade,
        String cor,
        String tamanho
) { }

