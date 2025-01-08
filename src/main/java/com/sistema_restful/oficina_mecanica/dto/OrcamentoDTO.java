// OrcamentoDTO.java
package com.sistema_restful.oficina_mecanica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
public class OrcamentoDTO {
    private Long clienteId;
    private List<ServicoQuantidadeDTO> servicos;
    private List<PecaQuantidadeDTO> pecas;

    @AllArgsConstructor
    @Data
    public static class ServicoQuantidadeDTO {
        private Long servicoId;
        private Integer quantidade;
    }
    @AllArgsConstructor
    @Data
    public static class PecaQuantidadeDTO {
        private Long pecaId;
        private Integer quantidade;
    }
}
