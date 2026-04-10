/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO
 */
package com.gabriel_nunez.oficina_mecanica.dto;

public class ProdutoMaisPedidoDTO {
    private Integer idProduto;
    private String nomePeca;
    private String linkFoto;
    private double precoPeca;
    private double precoPromo;
    private Long quantidadeTotal;
    private Double valorTotal;

    public ProdutoMaisPedidoDTO(Integer idProduto, String nomePeca, String linkFoto, double precoPeca, double precoPromo, Long quantidadeTotal, Double valorTotal) {
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

    public double getPrecoPeca() {
        return this.precoPeca;
    }

    public void setPrecoPeca(double precoPeca) {
        this.precoPeca = precoPeca;
    }

    public double getPrecoPromo() {
        return this.precoPromo;
    }

    public void setPrecoPromo(double precoPromo) {
        this.precoPromo = precoPromo;
    }

    public Long getQuantidadeTotal() {
        return this.quantidadeTotal;
    }

    public void setQuantidadeTotal(Long quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}

