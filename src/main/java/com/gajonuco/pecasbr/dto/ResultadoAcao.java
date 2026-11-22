package com.gajonuco.pecasbr.dto;

public record ResultadoAcao(
        TipoAcaoAgente tipo,
        boolean sucesso,
        String detalhe
) { }
