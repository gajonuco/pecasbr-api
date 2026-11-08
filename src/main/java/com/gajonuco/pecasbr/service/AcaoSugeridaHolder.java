package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.AcaoSugerida;
import org.springframework.stereotype.Component;

@Component
public class AcaoSugeridaHolder {

    private final ThreadLocal<AcaoSugerida> acaoAtual = new ThreadLocal<>();

    public void definir(AcaoSugerida acao) {
        acaoAtual.set(acao);
    }

    public AcaoSugerida consumir() {
        AcaoSugerida acao = acaoAtual.get();
        acaoAtual.remove();
        return acao;
    }
}