package com.gajonuco.pecasbr.integration.dto;

import java.time.LocalDate;

public record DTOCadastroCliente(
        String nome,
        String email,
        String senha,
        String cpf,
        String telefone,
        LocalDate dataNasc) {
}
