package com.gabriel_nunez.oficina_mecanica.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServicoMecanicoAgendadoRequestDTO {
    private Long clienteId;
    private Long veiculoId;
    private Long tipoServicoId;
    private List<Long> pecasUtilizadasIds;
    private List<String> mecanicosResponsaveisIds;
    private LocalDateTime dataInicio;
    private LocalDateTime dataConclusao;
}
