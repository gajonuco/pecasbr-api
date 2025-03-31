package com.gabriel_nunez.oficina_mecanica.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cliente> clientes;
}
