// OrcamentoPeca.java
package com.sistema_restful.oficina_mecanica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrcamentoPeca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference // Impede a serialização de Orcamento em OrcamentoPeca
    private Orcamento orcamento;

    @ManyToOne
    private Peca peca;

    private Integer quantidade;
}
