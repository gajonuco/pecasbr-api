package com.gabriel_nunez.oficina_mecanica.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoServicoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorBase;
}
