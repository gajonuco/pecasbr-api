package com.gabriel_nunez.oficina_mecanica.dto.response;



import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
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
