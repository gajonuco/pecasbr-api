package com.gajonuco.pecasbr.dto;

public record EnderecoDTO(
        String apelido,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado) {
}
