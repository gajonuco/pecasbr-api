package com.gabriel_nunez.oficina_mecanica.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServicoMecanicoRequestDTO {
    private Long clienteId;
    private Long veiculoId;
    private List<Long> pecasUtilizadasIds;
    private List<String> mecanicosResponsaveisIds;
    private BigDecimal valorTotal;
    private LocalDateTime dataInicio;
    private LocalDateTime dataConclusao;
}
