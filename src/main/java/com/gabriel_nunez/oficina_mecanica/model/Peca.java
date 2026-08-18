/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  com.gabriel_nunez.oficina_mecanica.model.PecaImagem
 *  com.gabriel_nunez.oficina_mecanica.model.PecaVariacao
 *  jakarta.persistence.CascadeType
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.FetchType
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.OneToMany
 *  jakarta.persistence.OrderBy
 *  jakarta.persistence.Table
 */
package com.gabriel_nunez.oficina_mecanica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.PecaImagem;
import com.gabriel_nunez.oficina_mecanica.model.PecaVariacao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_peca")
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_peca")
    private Integer id;
    @Column(name = "nome_peca", length = 100, nullable = false)
    @JsonProperty(value = "nome")
    private String nome;
    @Column(name = "detalhe_peca", columnDefinition = "TEXT", nullable = false)
    @JsonProperty(value = "detalhe")
    private String detalhe;
    @Column(name = "link_foto", length = 255, nullable = false)
    @JsonProperty(value = "linkFoto")
    private String linkFoto;
    @Column(name = "preco_peca", nullable = false)
    @JsonProperty(value = "preco")
    private double preco;
    @Column(name = "preco_promocional", nullable = false)
    @JsonProperty(value = "precoPromo")
    private double precoPromo;
    @Column(name = "disponivel")
    @JsonProperty(value = "disponivel")
    private int disponivel;
    @Column(name = "destaque")
    @JsonProperty(value = "destaque")
    private Integer destaque;
    @Column(name = "pronta_entrega")
    @JsonProperty(value = "prontaEntrega")
    private Integer prontaEntrega;
    @Column(name = "quantidade_estoque", nullable = false)
    @JsonProperty(value = "quantidadeEstoque")
    private Integer quantidadeEstoque;
    @Column(name = "estoque_minimo", nullable = false)
    @JsonProperty(value = "estoqueMinimo")
    private Integer estoqueMinimo;
    @Column(name = "estoque_critico", nullable = false)
    @JsonProperty(value = "estoqueCritico")
    private Integer estoqueCritico;
    @Column(name = "cor_unica")
    @JsonProperty(value = "corUnica")
    private Boolean corUnica = false;
    @Column(name = "tamanho_unico")
    @JsonProperty(value = "tamanhoUnico")
    private Boolean tamanhoUnico = false;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria_peca")
    @JsonProperty(value = "categoriaPeca")
    private CategoriaPeca categoriaPeca;
    @OneToMany(mappedBy = "peca", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy(value = "ordem ASC")
    @JsonProperty(value = "imagens")
    private List<PecaImagem> imagens = new ArrayList();
    @OneToMany(mappedBy = "peca", cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PecaVariacao> variacoes = new ArrayList();

    @JsonIgnore
    public int getEstoqueTotalVariacoes() {
        if (this.variacoes == null || this.variacoes.isEmpty()) {
            return this.quantidadeEstoque;
        }
        return this.variacoes.stream().mapToInt(v -> v.getQuantidadeEstoque() != null ? v.getQuantidadeEstoque() : 0)
                .sum();
    }

    @JsonIgnore
    public String getImagemPrincipal() {
        return this.imagens.stream().filter(img -> img.getPrincipal() != null && img.getPrincipal() == 1)
                .map(PecaImagem::getLinkImagem).findFirst().orElse(this.linkFoto);
    }

    public List<PecaImagem> getImagens() {
        return this.imagens;
    }

    public void setImagens(List<PecaImagem> imagens) {
        this.imagens = imagens;
    }

    @JsonIgnore
    public boolean isEstoqueBaixo() {
        return this.quantidadeEstoque <= this.estoqueMinimo && this.quantidadeEstoque > this.estoqueCritico;
    }

    @JsonIgnore
    public boolean isEstoqueCritico() {
        return this.quantidadeEstoque <= this.estoqueCritico && this.quantidadeEstoque > 0;
    }

    @JsonIgnore
    public boolean isEstoqueZerado() {
        return this.quantidadeEstoque <= 0;
    }

    @JsonIgnore
    public String getStatusEstoque() {
        if (this.isEstoqueZerado()) {
            return "ESGOTADO";
        }
        if (this.isEstoqueCritico()) {
            return "CRITICO";
        }
        if (this.isEstoqueBaixo()) {
            return "BAIXO";
        }
        return "NORMAL";
    }

    @JsonIgnore
    public boolean podeVender(int quantidade) {
        return this.quantidadeEstoque >= quantidade;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhe() {
        return this.detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getLinkFoto() {
        return this.linkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPrecoPromo() {
        return this.precoPromo;
    }

    public void setPrecoPromo(double precoPromo) {
        this.precoPromo = precoPromo;
    }

    public int getDisponivel() {
        return this.disponivel;
    }

    public void setDisponivel(int disponivel) {
        this.disponivel = disponivel;
    }

    public Integer getDestaque() {
        return this.destaque;
    }

    public void setDestaque(Integer destaque) {
        this.destaque = destaque;
    }

    public Integer getProntaEntrega() {
        return this.prontaEntrega;
    }

    public void setProntaEntrega(Integer prontaEntrega) {
        this.prontaEntrega = prontaEntrega;
    }

    public Integer getQuantidadeEstoque() {
        return this.quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        if (this.variacoes == null || this.variacoes.isEmpty()) {
            this.quantidadeEstoque = quantidadeEstoque;
        }
    }

    public Integer getEstoqueMinimo() {
        return this.estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public Integer getEstoqueCritico() {
        return this.estoqueCritico;
    }

    public void setEstoqueCritico(Integer estoqueCritico) {
        System.out.println("\ud83d\udd25 SETTER CHAMADO - setEstoqueCritico: " + estoqueCritico);
        this.estoqueCritico = estoqueCritico;
    }

    public CategoriaPeca getCategoriaPeca() {
        return this.categoriaPeca;
    }

    public void setCategoriaPeca(CategoriaPeca categoriaPeca) {
        this.categoriaPeca = categoriaPeca;
    }

    public Boolean getCorUnica() {
        return this.corUnica;
    }

    public void setCorUnica(Boolean corUnica) {
        this.corUnica = corUnica;
    }

    public Boolean getTamanhoUnico() {
        return this.tamanhoUnico;
    }

    public void setTamanhoUnico(Boolean tamanhoUnico) {
        this.tamanhoUnico = tamanhoUnico;
    }

    public List<PecaVariacao> getVariacoes() {
        return this.variacoes;
    }

    public void setVariacoes(List<PecaVariacao> variacoes) {
        this.variacoes = variacoes;
    }

    public void sincronizarEstoqueTotal() {
        if (this.variacoes != null && !this.variacoes.isEmpty()) {
            this.quantidadeEstoque = this.variacoes.stream()
                    .mapToInt(v -> v.getQuantidadeEstoque() != null ? v.getQuantidadeEstoque() : 0).sum();
        }
    }
}
