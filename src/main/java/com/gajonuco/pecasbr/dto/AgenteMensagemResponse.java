package com.gajonuco.pecasbr.dto;

public record AgenteMensagemResponse(String resposta, AcaoSugerida acaoSugerida) {

    public static AgenteMensagemResponse semAcao(String resposta) {
        return new AgenteMensagemResponse(resposta, null);
    }
}