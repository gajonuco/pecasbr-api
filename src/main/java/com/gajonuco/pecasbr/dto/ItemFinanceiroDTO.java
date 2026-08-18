/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.ItemFinanceiroDTO
 */
package com.gajonuco.pecasbr.dto;

import java.time.LocalDate;

public class ItemFinanceiroDTO {
    private int numSeq;
    private int idPedido;
    private String nomeCliente;
    private String telefone;
    private int numParcela;
    private int totalParcelas;
    private LocalDate dataVencimento;
    private double valorBruto;
    private int idFormaPagamento;
    private String formaPagamento;
    private double percentRetencao;
    private double valorRetencao;
    private double valorReceber;
    private int status;

    public ItemFinanceiroDTO(int numSeq, int idPedido, String nomeCliente, String telefone, int numParcela, int totalParcelas, LocalDate dataVencimento, double valorBruto, int idForma, String formaPagamento, double percentRetencao, double valorRetencao, double valorReceber, int status) {
        this.numSeq = numSeq;
        this.idPedido = idPedido;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
        this.numParcela = numParcela;
        this.totalParcelas = totalParcelas;
        this.dataVencimento = dataVencimento;
        this.valorBruto = valorBruto;
        this.idFormaPagamento = idForma;
        this.formaPagamento = formaPagamento;
        this.percentRetencao = percentRetencao;
        this.valorRetencao = valorRetencao;
        this.valorReceber = valorReceber;
        this.status = status;
    }

    public int getNumSeq() {
        return this.numSeq;
    }

    public void setNumSeq(int numSeq) {
        this.numSeq = numSeq;
    }

    public int getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValorBruto() {
        return this.valorBruto;
    }

    public void setValorBruto(double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public String getFormaPagamento() {
        return this.formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getPercentRetencao() {
        return this.percentRetencao;
    }

    public void setPercentRetencao(double percentRetencao) {
        this.percentRetencao = percentRetencao;
    }

    public double getValorRetencao() {
        return this.valorRetencao;
    }

    public void setValorRetencao(double valorRetencao) {
        this.valorRetencao = valorRetencao;
    }

    public double getValorReceber() {
        return this.valorReceber;
    }

    public void setValorReceber(double valorReceber) {
        this.valorReceber = valorReceber;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdFormaPagamento() {
        return this.idFormaPagamento;
    }

    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public String toString() {
        return "ItemFinanceiroDTO [numSeq=" + this.numSeq + ", idPedido=" + this.idPedido + ", nomeCliente=" + this.nomeCliente + ", telefone=" + this.telefone + ", numParcela=" + this.numParcela + ", totalParcelas=" + this.totalParcelas + ", dataVencimento=" + String.valueOf(this.dataVencimento) + ", valorBruto=" + this.valorBruto + ", idFormaPagamento=" + this.idFormaPagamento + ", formaPagamento=" + this.formaPagamento + ", percentRetencao=" + this.percentRetencao + ", valorRetencao=" + this.valorRetencao + ", valorReceber=" + this.valorReceber + ", status=" + this.status + "]";
    }
}

