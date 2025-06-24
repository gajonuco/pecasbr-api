/*package com.gabriel_nunez.oficina_mecanica.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "solicitacao_servico")
public class SolicitacaoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeServico;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteUsuario cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_servico_id", referencedColumnName = "id", nullable = false)
    private TipoServico tipoServico;

    private LocalDateTime dataHora;
}*/