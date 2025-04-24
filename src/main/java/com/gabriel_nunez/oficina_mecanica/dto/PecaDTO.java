package com.gabriel_nunez.oficina_mecanica.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PecaDTO {

    @NotBlank(message = "O nome da peça é obrigatório")
    private String nome;

    @NotBlank(message = "O SKU é obrigatório")
    private String sku;

    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantidadeEmEstoque;

    @NotNull(message = "A data de vencimento é obrigatória")
    private LocalDate dataVencimento;
}
