package com.gabriel_nunez.oficina_mecanica.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    @NotBlank(message = "O tipo de veículo é obrigatório")
    private String tipo;

    @NotBlank(message = "A placa é obrigatória")
    @Pattern(
        regexp = "^[A-Z]{3}\\d[A-Z]\\d{2}$", 
        message = "A placa deve seguir o padrão Mercosul (ex: ABC1D23)"
    )
    private String placa;

    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    private Integer ano;
    private Integer quilometragem;
}

