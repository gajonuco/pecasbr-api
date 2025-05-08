package com.gabriel_nunez.oficina_mecanica.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.gabriel_nunez.oficina_mecanica.enums.StatusOrcamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "orcamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @ToString.Exclude
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    @ToString.Exclude
    private Veiculo veiculo;

    @ManyToMany
    @JoinTable(
        name = "orcamento_peca",
        joinColumns = @JoinColumn(name = "orcamento_id"),
        inverseJoinColumns = @JoinColumn(name = "peca_id")
    )
    @ToString.Exclude
    private List<Peca> pecas;

    @ManyToMany
    @JoinTable(
        name = "orcamento_servico",
        joinColumns = @JoinColumn(name = "orcamento_id"),
        inverseJoinColumns = @JoinColumn(name = "tipo_servico_id")
    )
    @ToString.Exclude
    private List<TipoServico> tiposServico;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDate dataCriacao;

    @Column(nullable = false)
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private StatusOrcamento status;

    private String observacoes;
}
