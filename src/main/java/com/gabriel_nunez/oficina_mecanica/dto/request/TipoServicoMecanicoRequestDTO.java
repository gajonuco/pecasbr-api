package com.gabriel_nunez.oficina_mecanica.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoServicoMecanicoRequestDTO {
    @NotBlank(message = "O nome do serviço é obrigatório.")
    private String nome;

    private String descricao;

    @NotNull(message = "O valor base do serviço é obrigatório.")
    private BigDecimal valorBase;
}
