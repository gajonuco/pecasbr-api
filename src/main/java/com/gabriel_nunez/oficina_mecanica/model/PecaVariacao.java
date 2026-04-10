/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  com.gabriel_nunez.oficina_mecanica.model.PecaVariacao
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.FetchType
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.Table
 *  jakarta.persistence.UniqueConstraint
 */
package com.gabriel_nunez.oficina_mecanica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="tbl_peca_variacao", uniqueConstraints={@UniqueConstraint(name="uk_peca_cor_tamanho", columnNames={"id_peca", "cor", "tamanho"})})
public class PecaVariacao {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_variacao")
    private Integer id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_peca", nullable=false)
    @JsonIgnore
    private Peca peca;
    @Column(name="cor", length=50, nullable=false)
    @JsonProperty(value="cor")
    private String cor;
    @Column(name="hex_code", length=7)
    @JsonProperty(value="hexCode")
    private String hexCode;
    @Column(name="tamanho", length=20, nullable=false)
    @JsonProperty(value="tamanho")
    private String tamanho;
    @Column(name="quantidade_estoque", nullable=false)
    @JsonProperty(value="quantidadeEstoque")
    private Integer quantidadeEstoque = 0;
    @Column(name="sku", length=100)
    @JsonProperty(value="sku")
    private String sku;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Peca getPeca() {
        return this.peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getHexCode() {
        return this.hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getQuantidadeEstoque() {
        return this.quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}

