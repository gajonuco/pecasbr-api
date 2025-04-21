package com.gabriel_nunez.oficina_mecanica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClienteDTO {

    @NotBlank(message = "Login é obrigatório")
    @Email(message = "E-mail inválido")
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF/CNPJ é obrigatório")
    private String cpfCnpj;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
    private String cep;

    private List<String> placas;
}

