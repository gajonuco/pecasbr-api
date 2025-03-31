package com.sistema_restful.oficina_mecanica.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrcamentoResponseDTO {
    private Long id;
    private LocalDate dataCriacao;
    private Double valorTotal;
    private Double valorDesconto;
    private Long clienteId;
    private List<OrcamentoDTO.ServicoQuantidadeDTO> servicos;
    private List<OrcamentoDTO.PecaQuantidadeDTO> pecas;
}
