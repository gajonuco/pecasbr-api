package com.gabriel_nunez.oficina_mecanica.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pecas")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidadeEmEstoque;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    private boolean obsoleta = false;
}
