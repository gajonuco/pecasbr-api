package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.AgenteMensagemRequest;
import com.gajonuco.pecasbr.dto.AgenteMensagemResponse;
import com.gajonuco.pecasbr.service.AgenteService;
import dev.langchain4j.exception.RateLimitException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agente")
public class AgenteController {

    private static final Logger log = LoggerFactory.getLogger(AgenteController.class);

    private final AgenteService agenteService;

    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @PostMapping("/mensagem")
    public ResponseEntity<?> conversar(@Valid @RequestBody AgenteMensagemRequest request) {
        try {
            String resposta = agenteService.responder(request.mensagem());
            return ResponseEntity.ok(new AgenteMensagemResponse(resposta));
        } catch (RateLimitException e) {
            log.warn("Limite de requisições do Gemini atingido");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("O assistente está sobrecarregado no momento. Tente novamente em instantes.");
        } catch (Exception e) {
            log.error("Erro ao chamar o agente de IA", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Não foi possível processar sua mensagem agora. Tente novamente.");
        }
    }
}