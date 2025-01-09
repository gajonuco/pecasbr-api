package com.sistema_restful.oficina_mecanica.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Embeddable
public class Endereco {

    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;
}

