package com.gabriel_nunez.oficina_mecanica.dto;

import com.gabriel_nunez.oficina_mecanica.user.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(

    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    String cpfCnpj,

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    String telefone,

    @Email(message = "E-mail inválido")
    String login, // email será o login

    @NotBlank(message = "A senha é obrigatória")
    String password,

    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
    String cep,

    UserRole role
) {}
