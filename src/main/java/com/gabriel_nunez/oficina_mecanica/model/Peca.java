package com.gabriel_nunez.oficina_mecanica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_peca")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_peca")
    private Integer id;

    @Column(name = "nome_peca", length = 100, nullable = false)
    @JsonProperty("nome")
    private String nome;

    @Column(name = "detalhe_peca", length = 500, nullable = false)
    @JsonProperty("detalhe")
    private String detalhe;

    @Column(name = "link_foto", length = 255, nullable = false)
    @JsonProperty("linkFoto")
    private String linkFoto;

    @Column(name = "preco_peca", nullable = false)
    @JsonProperty("preco")
    private double preco;

    @Column(name = "preco_promocional", nullable = false)
    @JsonProperty("precoPromo")
    private double precoPromo;

    @Column(name = "disponivel")
    @JsonProperty("disponivel")
    private int disponivel;

    @Column(name = "destaque")
    @JsonProperty("destaque")
    private Integer destaque;

    @Column(name = "pronta_entrega")
    @JsonProperty("prontaEntrega")
    private Integer prontaEntrega;

    @Column(name = "quantidade_estoque", nullable = false)
    @JsonProperty("quantidadeEstoque")
    private Integer quantidadeEstoque;

    @Column(name = "estoque_minimo", nullable = false)
    @JsonProperty("estoqueMinimo")
    private Integer estoqueMinimo;

    @Column(name = "estoque_critico", nullable = false)
    @JsonProperty("estoqueCritico")  // ← ADICIONE ESTA ANOTAÇÃO
    private Integer estoqueCritico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria_peca")
    @JsonProperty("categoriaPeca")
    private CategoriaPeca categoriaPeca;

    // Métodos auxiliares
    @JsonIgnore
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque <= estoqueMinimo && quantidadeEstoque > estoqueCritico;
    }

    @JsonIgnore
    public boolean isEstoqueCritico() {
        return quantidadeEstoque <= estoqueCritico && quantidadeEstoque > 0;
    }

    @JsonIgnore
    public boolean isEstoqueZerado() {
        return quantidadeEstoque <= 0;
    }

    @JsonIgnore
    public String getStatusEstoque() {
        if (isEstoqueZerado())
            return "ESGOTADO";
        if (isEstoqueCritico())
            return "CRITICO";
        if (isEstoqueBaixo())
            return "BAIXO";
        return "NORMAL";
    }

    @JsonIgnore
    public boolean podeVender(int quantidade) {
        return quantidadeEstoque >= quantidade;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getLinkFoto() {
        return linkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPrecoPromo() {
        return precoPromo;
    }

    public void setPrecoPromo(double precoPromo) {
        this.precoPromo = precoPromo;
    }

    public int getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }

    public Integer getDestaque() {
        return destaque;
    }

    public void setDestaque(Integer destaque) {
        this.destaque = destaque;
    }

    public Integer getProntaEntrega() {
        return prontaEntrega;
    }

    public void setProntaEntrega(Integer prontaEntrega) {
        this.prontaEntrega = prontaEntrega;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Integer getEstoqueCritico() {
        return estoqueCritico;
    }

    public void setEstoqueCritico(Integer estoqueCritico) {
        System.out.println("🔥 SETTER CHAMADO - setEstoqueCritico: " + estoqueCritico);
        this.estoqueCritico = estoqueCritico;
    }

    public CategoriaPeca getCategoriaPeca() {
        return categoriaPeca;
    }

    public void setCategoriaPeca(CategoriaPeca categoriaPeca) {
        this.categoriaPeca = categoriaPeca;
    }
}