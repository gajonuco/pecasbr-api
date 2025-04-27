package com.gabriel_nunez.oficina_mecanica.dto.response;

import com.gabriel_nunez.oficina_mecanica.enums.StatusServico;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ServicoMecanicoResponseDTO {
    private Long id;
    private StatusServico status;
    private BigDecimal valorTotal;
    private LocalDateTime dataInicio;
    private LocalDateTime dataConclusao;
    private Long clienteId;
    private Long veiculoId;
    private List<Long> pecasUtilizadasIds;
    private List<String> mecanicosResponsaveisIds;
}
