package com.gajonuco.pecasbr.config;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeminiConfigChatMemoryTest {

    private final GeminiConfig geminiConfig = new GeminiConfig();

    @Test
    void devePreservarMensagensDentroDaMesmaSessao() {
        ChatMemoryProvider provider = geminiConfig.chatMemoryProvider();
        ChatMemory memoria = provider.get("sessao-A");

        memoria.add(UserMessage.from("Meu nome é Gabriel"));

        assertThat(memoria.messages()).hasSize(1);
    }

    @Test
    void naoDeveCompartilharMemoriaEntreSessoesDiferentes() {
        ChatMemoryProvider provider = geminiConfig.chatMemoryProvider();

        ChatMemory memoriaA = provider.get("sessao-A");
        memoriaA.add(UserMessage.from("Meu nome é Gabriel"));

        ChatMemory memoriaB = provider.get("sessao-B");

        assertThat(memoriaB.messages()).isEmpty();
    }

    @Test
    void deveRetornarAMesmaInstanciaDeMemoriaParaOMesmoSessionId() {
        ChatMemoryProvider provider = geminiConfig.chatMemoryProvider();

        ChatMemory primeira = provider.get("sessao-A");
        primeira.add(UserMessage.from("Primeira mensagem"));

        ChatMemory segunda = provider.get("sessao-A");

        assertThat(segunda.messages()).hasSize(1);
    }
}