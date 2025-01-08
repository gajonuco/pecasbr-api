// Servico.java
package com.sistema_restful.oficina_mecanica.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double preco;
}
