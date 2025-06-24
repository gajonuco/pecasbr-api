package com.gabriel_nunez.oficina_mecanica.dto.request;

import java.math.BigDecimal;
import com.gabriel_nunez.oficina_mecanica.enums.TipoOferta;
import lombok.Data;

@Data
public class OrcamentosServicoMecanicoRequestDTO {
    private String descricao;
    private TipoOferta tipoOferta;
    private BigDecimal preco;
    private String duracaoEstimada;
    private Integer garantiaMeses;
    private boolean ativo;
    private Long tipoServicoId;
    private Long pecaId;
    private String loginCliente; // <--- NOVO campo
}
