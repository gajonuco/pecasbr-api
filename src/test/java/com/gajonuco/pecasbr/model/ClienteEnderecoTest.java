package com.gajonuco.pecasbr.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClienteEnderecoTest {

    @Test
    void deveRetornarEnderecoMarcadoComoPrincipal() {
        Cliente cliente = new Cliente();
        Endereco casa = new Endereco();
        casa.setApelido("Casa");
        casa.setPrincipal(false);
        Endereco trabalho = new Endereco();
        trabalho.setApelido("Trabalho");
        trabalho.setPrincipal(true);

        cliente.setEnderecos(List.of(casa, trabalho));

        assertEquals("Trabalho", cliente.getEnderecoPrincipal().getApelido());
    }

    @Test
    void deveRetornarPrimeiroEnderecoQuandoNenhumForPrincipal() {
        Cliente cliente = new Cliente();
        Endereco unico = new Endereco();
        unico.setApelido("Único");
        cliente.setEnderecos(List.of(unico));

        assertEquals("Único", cliente.getEnderecoPrincipal().getApelido());
    }

    @Test
    void deveRetornarNullQuandoClienteNaoTiverEndereco() {
        Cliente cliente = new Cliente();
        assertNull(cliente.getEnderecoPrincipal());
    }
}