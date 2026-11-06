package com.gajonuco.pecasbr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gajonuco.pecasbr.dto.AgenteMensagemRequest;
import com.gajonuco.pecasbr.security.JWTTokenUtil;
import com.gajonuco.pecasbr.service.AgenteService;
import dev.langchain4j.exception.RateLimitException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
          controllers = AgenteController.class,
           excludeAutoConfiguration = {
               org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
               org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
    })
@AutoConfigureMockMvc(addFilters = false) // testa só a camada web; segurança já validada separadamente via permitAll
class AgenteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgenteService agenteService;

    @MockitoBean
    private JWTTokenUtil jwtTokenUtil;

    @Test
    void deveResponderComSucessoQuandoAgenteProcessaMensagem() throws Exception {
        when(agenteService.responder(anyString(), anyString())).thenReturn("Olá! Como posso ajudar?");

        mockMvc.perform(post("/agente/mensagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AgenteMensagemRequest("sessao-teste-1","Oi, tudo bem?"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resposta").value("Olá! Como posso ajudar?"));
    }

    @Test
    void deveRetornar429QuandoGeminiAtingeLimiteDeTaxa() throws Exception {
        when(agenteService.responder(anyString(), anyString()))
                .thenThrow(new RateLimitException("quota excedida"));

        mockMvc.perform(post("/agente/mensagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AgenteMensagemRequest("sessao-teste-2","Quais produtos vocês têm?"))))
                .andExpect(status().isTooManyRequests());
    }

    @Test
    void deveRetornar400QuandoMensagemEstiverVazia() throws Exception {
        mockMvc.perform(post("/agente/mensagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AgenteMensagemRequest("sessao-teste-3",""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoSesseionIdEstiverVazio() throws Exception{
        mockMvc.perform(post("/agente/mensagem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new AgenteMensagemRequest("", "Oi"))))
        .andExpect(status().isBadRequest());
    }
}