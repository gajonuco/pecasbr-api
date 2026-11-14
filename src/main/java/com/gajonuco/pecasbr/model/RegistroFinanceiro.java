/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.model.FormaPagamento
 *  com.gajonuco.pecasbr.model.Pedido
 *  com.gajonuco.pecasbr.model.RegistroFinanceiro
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.Table
 */
package com.gajonuco.pecasbr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="tbl_financeiro")
public class RegistroFinanceiro {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="num_seq")
    private int numSeq;
    @Column(name="num_parcela")
    private int numParcela;
    @Column(name="total_parcelas")
    private int totalParcelas;
    @Column(name="vencimento")
    private LocalDate vencimento;
    @Column(name="valor_bruto")
    private BigDecimal valorBruto;
    @Column(name="percent_retencao")
    private BigDecimal percentRetencao;
    @Column(name="valor_retencao")
    private BigDecimal valorRetencao;
    @Column(name="valor_liquido")
    private BigDecimal valorLiquido;
    @Column(name="status")
    private int status;
    @ManyToOne
    @JoinColumn(name="id_pedido")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name="forma_pgto")
    private FormaPagamento forma;

    public int getNumSeq() {
        return this.numSeq;
    }

    public void setNumSeq(int numSeq) {
        this.numSeq = numSeq;
    }

    public int getNumParcela() {
        return this.numParcela;
    }

    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    public int getTotalParcelas() {
        return this.totalParcelas;
    }

    public void setTotalParcelas(int totalParcelas) {
        this.totalParcelas = totalParcelas;
    }

    public LocalDate getVencimento() {
        return this.vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getValorBruto() {
        return this.valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getPercentRetencao() {
        return this.percentRetencao;
    }

    public void setPercentRetencao(BigDecimal percentRetencao) {
        this.percentRetencao = percentRetencao;
    }

    public BigDecimal getValorRetencao() {
        return this.valorRetencao;
    }

    public void setValorRetencao(BigDecimal valorRetencao) {
        this.valorRetencao = valorRetencao;
    }

    public BigDecimal getValorLiquido() {
        return this.valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public FormaPagamento getForma() {
        return this.forma;
    }

    public void setForma(FormaPagamento forma) {
        this.forma = forma;
    }

    public String toString() {
        return "RegistroFinanceiro [numSeq=" + this.numSeq + ", numParcela=" + this.numParcela + ", totalParcelas=" + this.totalParcelas + ", vencimento=" + String.valueOf(this.vencimento) + ", valorBruto=" + this.valorBruto + ", percentRetencao=" + this.percentRetencao + ", valorRetencao=" + this.valorRetencao + ", valorLiquido=" + this.valorLiquido + ", status=" + this.status + ", pedido=" + this.pedido.getId() + ", forma=" + this.forma.getNumSeq() + "]";
    }
}

