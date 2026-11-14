/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.ProdutoMaisPedidoDTO
 */
package com.gajonuco.pecasbr.dto;

import java.math.BigDecimal;

public class ProdutoMaisPedidoDTO {
    private Integer idProduto;
    private String nomePeca;
    private String linkFoto;
    private BigDecimal precoPeca;
    private BigDecimal precoPromo;
    private Long quantidadeTotal;
    private BigDecimal valorTotal;

    public ProdutoMaisPedidoDTO(Integer idProduto, String nomePeca, String linkFoto, BigDecimal precoPeca, BigDecimal precoPromo, Long quantidadeTotal, BigDecimal valorTotal) {
        this.idProduto = idProduto;
        this.nomePeca = nomePeca;
        this.linkFoto = linkFoto;
        this.precoPeca = precoPeca;
        this.precoPromo = precoPromo;
        this.quantidadeTotal = quantidadeTotal;
        this.valorTotal = valorTotal;
    }

    public Integer getidProduto() {
        return this.idProduto;
    }

    public void setidProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomePeca() {
        return this.nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public String getLinkFoto() {
        return this.linkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }

    public BigDecimal getPrecoPeca() {
        return this.precoPeca;
    }

    public void setPrecoPeca(BigDecimal precoPeca) {
        this.precoPeca = precoPeca;
    }

    public BigDecimal getPrecoPromo() {
        return this.precoPromo;
    }

    public void setPrecoPromo(BigDecimal precoPromo) {
        this.precoPromo = precoPromo;
    }

    public Long getQuantidadeTotal() {
        return this.quantidadeTotal;
    }

    public void setQuantidadeTotal(Long quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}

