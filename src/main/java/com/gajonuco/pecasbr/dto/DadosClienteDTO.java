package com.gajonuco.pecasbr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DadosClienteDTO(
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Email @Size(max = 100) String email,
        @NotBlank @Size(max = 20) String cpf,
        @NotBlank @Size(max = 20) String telefone,
        LocalDate dataNasc) {
}