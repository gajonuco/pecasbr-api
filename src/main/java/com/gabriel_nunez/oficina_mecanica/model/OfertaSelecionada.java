/*package com.gabriel_nunez.oficina_mecanica.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ofertas_selecionadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfertaSelecionada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "oferta_servico_id")
    private OfertaServico ofertaOriginal;

    @Column(nullable = false)
    private BigDecimal preco;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cliente_usuario_id", nullable = false)
    private ClienteUsuario clienteUsuario;

    private LocalDateTime dataHoraSelecionada;
}
*/