package com.gajonuco.pecasbr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AgenteMensagemRequest(

        @NotBlank(message = "O sessionId não pode estar vazio")
        String sessionId,

        @NotBlank(message = "A mensagem não pode estar vazia")
        @Size(max = 2000, message = "A mensagem deve ter no máximo 2000 caracteres")
        String mensagem

) {}