/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.gajonuco.pecasbr.model.ItemPedido
 *  com.gajonuco.pecasbr.model.Peca
 *  com.gajonuco.pecasbr.model.PecaVariacao
 *  com.gajonuco.pecasbr.model.Pedido
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.FetchType
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.Table
 */
package com.gajonuco.pecasbr.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name="tbl_itempedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int num_seq;
    @Column(name="qtdt_item", unique=true)
    private int qtdtItem;
    @Column(name="preco_unitario")
    private BigDecimal precoUnitario;
    @Column(name="preco_total")
    private BigDecimal precoTotal;
    @Column(name="cor_escolhida", length=50)
    private String corEscolhida;
    @Column(name="tamanho_escolhido", length=20)
    private String tamanhoEscolhido;
    @ManyToOne
    @JoinColumn(name="id_pedido")
    @JsonIgnoreProperties(value={"itensPedido"})
    private Pedido pedido;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_peca")
    private Peca peca;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_variacao")
    @JsonIgnoreProperties(value={"peca"})
    private PecaVariacao variacao;

    public int getNum_seq() {
        return this.num_seq;
    }

    public void setNum_seq(int num_seq) {
        this.num_seq = num_seq;
    }

    public int getQtdtItem() {
        return this.qtdtItem;
    }

    public void setQtdtItem(int qtdtItem) {
        this.qtdtItem = qtdtItem;
    }

    public BigDecimal getPrecoUnitario() {
        return this.precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getPrecoTotal() {
        return this.precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Peca getPeca() {
        return this.peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public String getCorEscolhida() {
        return this.corEscolhida;
    }

    public void setCorEscolhida(String corEscolhida) {
        this.corEscolhida = corEscolhida;
    }

    public String getTamanhoEscolhido() {
        return this.tamanhoEscolhido;
    }

    public void setTamanhoEscolhido(String tamanhoEscolhido) {
        this.tamanhoEscolhido = tamanhoEscolhido;
    }

    public PecaVariacao getVariacao() {
        return this.variacao;
    }

    public void setVariacao(PecaVariacao variacao) {
        this.variacao = variacao;
    }
}

