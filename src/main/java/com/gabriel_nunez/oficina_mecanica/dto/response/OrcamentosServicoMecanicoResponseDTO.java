package com.gabriel_nunez.oficina_mecanica.dto.response;

import java.math.BigDecimal;
import com.gabriel_nunez.oficina_mecanica.enums.TipoOferta;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrcamentosServicoMecanicoResponseDTO {
    private Long id;
    private String descricao;
    private TipoOferta tipoOferta;
    private BigDecimal preco;
    private String duracaoEstimada;
    private Integer garantiaMeses;
    private boolean ativo;
    private String nomeTipoServico;
    private String nomePeca;
    private String marcaPeca;
    private LocalDate dataVencimentoPeca;
     private String nomeCliente;
}