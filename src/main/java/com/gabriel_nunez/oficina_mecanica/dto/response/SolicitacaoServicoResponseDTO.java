package com.gabriel_nunez.oficina_mecanica.dto.response;

import java.time.LocalDateTime;
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
public class SolicitacaoServicoResponseDTO {
    private String nomeCliente;
    private String nomeServico;
    private LocalDateTime dataHora;
}
