package com.gajonuco.pecasbr.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_cliente")
    private int id;

    @Column(name =  "nome_cliente", length = 100, nullable = false)
    private String nome;

    @Column(name = "email_cliente", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "telefone_cliente", length = 20, nullable = false, unique = true)
    private String telefone;

    @Column(name = "data_nasc")
    @JsonFormat(pattern = "yyyy-MM-dd")  
    private LocalDate dataNasc;
    

    @Column(name = "cpf_cliente", nullable = false, unique = true)
    private String cpf;


    @Column(name = "senha", length = 100)
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("cliente")
    private List<Endereco> enderecos = new ArrayList<>();

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public List<Endereco> getEnderecos() { return enderecos; }
    public void setEnderecos(List<Endereco> enderecos) { this.enderecos = enderecos; }

    @Transient
    public Endereco getEnderecoPrincipal() {
        return enderecos.stream()
                .filter(Endereco::isPrincipal)
                .findFirst()
                .orElse(enderecos.isEmpty() ? null : enderecos.get(0));
    }

    

    public Cliente() {
    }

    public Cliente(String nome, LocalDate dataNasc, String telefone) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    
}
