package com.gajonuco.pecasbr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@MensagemOuResultadoAcaoPresente
public record AgenteMensagemRequest(

        @NotBlank(message = "O sessionId não pode estar vazio")
        String sessionId,

        @Size(max = 2000, message = "A mensagem deve ter no máximo 2000 caracteres")
        String mensagem,

        ResultadoAcao resultadoAcao

) {}