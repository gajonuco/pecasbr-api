package com.gabriel_nunez.oficina_mecanica.dto.response;

import java.time.LocalDateTime;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoServicoResponseDTO {
    private Long id;
    private String nomeCliente;
    private String loginCliente; // <-- Adicionado
    private String nomeServico;
    private LocalDateTime dataHora;
    private Long tipoServicoId;
    private Long clienteId;
}
