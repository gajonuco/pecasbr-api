package com.gajonuco.pecasbr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private int id;

    @Column(name = "apelido", length = 50)
    private String apelido; // "Casa", "Trabalho" etc — opcional

    @Column(name = "cep", length = 10, nullable = false)
    private String cep;

    @Column(name = "logradouro", length = 100)
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "complemento", length = 50)
    private String complemento;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "principal", nullable = false)
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnoreProperties({"enderecos","senha"})
    private Cliente cliente;

    public Endereco() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
