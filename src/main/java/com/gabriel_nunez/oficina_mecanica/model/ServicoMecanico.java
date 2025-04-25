package com.gabriel_nunez.oficina_mecanica.model;

import com.gabriel_nunez.oficina_mecanica.enums.StatusServico;
import com.gabriel_nunez.oficina_mecanica.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "servicos_mecanicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoMecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusServico status;

    private BigDecimal valorTotal;

    private LocalDateTime dataInicio;

    private LocalDateTime dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToMany
    @JoinTable(
        name = "servico_peca",
        joinColumns = @JoinColumn(name = "servico_id"),
        inverseJoinColumns = @JoinColumn(name = "peca_id")
    )
    private List<Peca> pecasUtilizadas;

    @ManyToMany
    @JoinTable(
        name = "servico_mecanico_user",
        joinColumns = @JoinColumn(name = "servico_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> mecanicosResponsaveis;
}
