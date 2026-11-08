package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.AcaoSugerida;
import com.gajonuco.pecasbr.dto.AgenteMensagemResponse;
import com.gajonuco.pecasbr.dto.ResultadoAcao;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class AgenteService {

    private final GeminiAssistant geminiAssistant;
    private final AcaoSugeridaHolder acaoSugeridaHolder;

    public AgenteService(GeminiAssistant geminiAssistant, AcaoSugeridaHolder acaoSugeridaHolder) {
        this.geminiAssistant = geminiAssistant;
        this.acaoSugeridaHolder = acaoSugeridaHolder;
    }

    public AgenteMensagemResponse responder(String sessionId, String mensagem, ResultadoAcao resultadoAcao) {
        String textoParaOAgente = resultadoAcao != null
                ? montarTextoDeConfirmacao(resultadoAcao)
                : mensagem;

        String resposta = geminiAssistant.chat(sessionId, textoParaOAgente);
        AcaoSugerida acao = acaoSugeridaHolder.consumir();

        return new AgenteMensagemResponse(resposta, acao);
    }

    private String montarTextoDeConfirmacao(ResultadoAcao resultadoAcao) {
        return resultadoAcao.sucesso()
                ? "[Confirmação do sistema] A operação foi concluída com sucesso: " + resultadoAcao.detalhe()
                : "[Confirmação do sistema] A operação FALHOU: " + resultadoAcao.detalhe();
    }
}