package com.sistema_restful.oficina_mecanica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class OrcamentoDTO {
    private Long id;
    private Long clienteId;
    private String descricao;
    private Double valor;
    private List<ServicoQuantidadeDTO> servicos; // Adicionado
    private List<PecaQuantidadeDTO> pecas; // Adicionado

    @Data
    @AllArgsConstructor
    public static class ServicoQuantidadeDTO {
        private Long servicoId;
        private Integer quantidade;
    }

    @Data
    @AllArgsConstructor
    public static class PecaQuantidadeDTO {
        private Long pecaId;
        private Integer quantidade;
    }
}

