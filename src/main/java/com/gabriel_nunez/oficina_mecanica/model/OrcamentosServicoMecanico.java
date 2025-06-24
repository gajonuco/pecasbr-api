package com.gabriel_nunez.oficina_mecanica.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.gabriel_nunez.oficina_mecanica.enums.TipoOferta;

@Entity
@Table(name = "orcamentos_servico_mecanico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrcamentosServicoMecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoOferta tipoOferta;

    @Column(nullable = false)
    private BigDecimal preco;

    private String duracaoEstimada;

    private Integer garantiaMeses;

    private boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "tipo_servico_id", nullable = false)
    private TipoServicoMecanico tipoServico;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "peca_id")
    private Peca peca;

    @ManyToOne(optional = true) // estava false
    @JoinColumn(name = "cliente_usuario_id")
    private ClienteUsuario clienteUsuario;


}
