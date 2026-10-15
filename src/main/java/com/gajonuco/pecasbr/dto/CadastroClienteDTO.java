package com.gajonuco.pecasbr.dto;

import java.time.LocalDate;

public record CadastroClienteDTO(
        String nome,
        String email,
        String senha,
        String cpf,
        String telefone,
        LocalDate dataNasc) {
}
