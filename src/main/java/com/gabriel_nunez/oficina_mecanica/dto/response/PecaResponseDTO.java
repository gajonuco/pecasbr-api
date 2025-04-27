package com.gabriel_nunez.oficina_mecanica.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PecaResponseDTO {
    private Long id;
    private String nome;
    private String sku;
    private String marca;
    private BigDecimal preco;
    private Integer quantidadeEmEstoque;
    private LocalDate dataVencimento;
    private boolean obsoleta;
}
