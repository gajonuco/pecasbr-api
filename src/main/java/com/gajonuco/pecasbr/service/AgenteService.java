
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.AcaoSugerida;
import com.gajonuco.pecasbr.dto.AgenteMensagemResponse;
import com.gajonuco.pecasbr.dto.ResultadoAcao;
import org.springframework.stereotype.Service;

@Service
public class AgenteService {

    private final GeminiAssistant geminiAssistant;
    private final AcaoSugeridaHolder acaoSugeridaHolder;

    public AgenteService(
            GeminiAssistant geminiAssistant,
            AcaoSugeridaHolder acaoSugeridaHolder
    ) {
        this.geminiAssistant = geminiAssistant;
        this.acaoSugeridaHolder = acaoSugeridaHolder;
    }

    public AgenteMensagemResponse responder(
            String sessionId,
            String mensagem,
            ResultadoAcao resultadoAcao
    ) {
        String textoParaOAgente = resultadoAcao != null
                ? montarTextoDeConfirmacao(resultadoAcao)
                : mensagem;

        String resposta = geminiAssistant.chat(
                sessionId,
                textoParaOAgente
        );

        AcaoSugerida acao = acaoSugeridaHolder.consumir();

        if (acao != null) {
            return new AgenteMensagemResponse(null, acao);
        }

        return new AgenteMensagemResponse(resposta, null);
    }

    private String montarTextoDeConfirmacao(ResultadoAcao resultadoAcao) {
        return resultadoAcao.sucesso()
                ? """
                O frontend confirmou que a operação foi executada
                com sucesso.
                
                Detalhes: %s.
                
                Informe o resultado ao cliente de maneira natural.
                """.formatted(resultadoAcao.detalhe())
                : """
                O frontend informou que a operação não foi executada
                com sucesso.
                
                Motivo: %s.
                
                Explique ao cliente que não foi possível concluir
                a operação.
                """.formatted(resultadoAcao.detalhe());
    }
}
