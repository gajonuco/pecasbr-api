package com.gabriel_nunez.oficina_mecanica.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaSelecionadaRequestDTO {
    private Long ofertaOriginalId;
    private BigDecimal preco;
    private String descricao;
    private Long clienteUsuarioId;
}
