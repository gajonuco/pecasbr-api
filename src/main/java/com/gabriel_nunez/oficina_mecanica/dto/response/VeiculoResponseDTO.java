package com.gabriel_nunez.oficina_mecanica.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoResponseDTO {
    private Long id;
    private String tipo;
    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private Integer quilometragem;
}
