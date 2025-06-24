package com.gabriel_nunez.oficina_mecanica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicoMecanicoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valorBase;
}
