package com.gabriel_nunez.oficina_mecanica.dto;

public class AlertaEstoqueDTO {
    
    private Integer idPeca;
    private String nomePeca;
    private Integer quantidadeAtual;
    private Integer estoqueMinimo;
    private String statusEstoque;
    private String nivelUrgencia; // ALTA, MEDIA
    private String mensagem;
    public Integer getIdPeca() {
        return idPeca;
    }
    public void setIdPeca(Integer idPeca) {
        this.idPeca = idPeca;
    }
    public String getNomePeca() {
        return nomePeca;
    }
    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }
    public Integer getQuantidadeAtual() {
        return quantidadeAtual;
    }
    public void setQuantidadeAtual(Integer quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }
    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }
    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
    public String getStatusEstoque() {
        return statusEstoque;
    }
    public void setStatusEstoque(String statusEstoque) {
        this.statusEstoque = statusEstoque;
    }
    public String getNivelUrgencia() {
        return nivelUrgencia;
    }
    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    
}
