// Orcamento.java
package com.sistema_restful.oficina_mecanica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Gerenciar a serialização de orcamentoServicos
    private List<OrcamentoServico> orcamentoServicos = new ArrayList<>();

    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Gerenciar a serialização de orcamentoPecas
    private List<OrcamentoPeca> orcamentoPecas = new ArrayList<>();

    private LocalDate dataCriacao;
    private Double valorTotal;
    private Double valorDesconto;

    public void calcularValorTotal() {
        // Calcular o total dos serviços considerando a quantidade
        double totalServicos = orcamentoServicos.stream()
                .mapToDouble(os -> os.getServico().getPreco() * os.getQuantidade())
                .sum();

        // Calcular o total das peças considerando a quantidade
        double totalPecas = orcamentoPecas.stream()
                .mapToDouble(op -> op.getPeca().getPreco() * op.getQuantidade())
                .sum();

        // Soma total
        valorTotal = totalServicos + totalPecas;

        // Aplicar desconto de 10% se houver ao menos um serviço e uma peça
        if (!orcamentoServicos.isEmpty() && !orcamentoPecas.isEmpty()) {
            valorDesconto = valorTotal * 0.10;
            valorTotal -= valorDesconto;
        } else {
            valorDesconto = 0.0;
        }
    }
}
