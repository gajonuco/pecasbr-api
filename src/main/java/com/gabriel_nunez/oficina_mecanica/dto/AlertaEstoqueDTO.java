/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.AlertaEstoqueDTO
 */
package com.gabriel_nunez.oficina_mecanica.dto;

public class AlertaEstoqueDTO {
    private Integer idPeca;
    private String nomePeca;
    private Integer quantidadeAtual;
    private Integer estoqueMinimo;
    private String statusEstoque;
    private String nivelUrgencia;
    private String mensagem;

    public Integer getIdPeca() {
        return this.idPeca;
    }

    public void setIdPeca(Integer idPeca) {
        this.idPeca = idPeca;
    }

    public String getNomePeca() {
        return this.nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public Integer getQuantidadeAtual() {
        return this.quantidadeAtual;
    }

    public void setQuantidadeAtual(Integer quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public Integer getEstoqueMinimo() {
        return this.estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public String getStatusEstoque() {
        return this.statusEstoque;
    }

    public void setStatusEstoque(String statusEstoque) {
        this.statusEstoque = statusEstoque;
    }

    public String getNivelUrgencia() {
        return this.nivelUrgencia;
    }

    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

