package com.gabriel_nunez.oficina_mecanica.dto;

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
	private int    idFormaPagamento;
	private String formaPagamento;
	private double percentRetencao;
	private double valorRetencao;
	private double valorReceber;
	private int    status;
	
	
	public ItemFinanceiroDTO(int numSeq, int idPedido, String nomeCliente, String telefone, int numParcela,
			int totalParcelas, LocalDate dataVencimento, double valorBruto, int idForma,  String formaPagamento,
			double percentRetencao, double valorRetencao, double valorReceber, int status) {
		super();
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
		return numSeq;
	}
	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
	}
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public int getNumParcela() {
		return numParcela;
	}
	public void setNumParcela(int numParcela) {
		this.numParcela = numParcela;
	}
	public int getTotalParcelas() {
		return totalParcelas;
	}
	public void setTotalParcelas(int totalParcelas) {
		this.totalParcelas = totalParcelas;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public double getValorBruto() {
		return valorBruto;
	}
	public void setValorBruto(double valorBruto) {
		this.valorBruto = valorBruto;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public double getPercentRetencao() {
		return percentRetencao;
	}
	public void setPercentRetencao(double percentRetencao) {
		this.percentRetencao = percentRetencao;
	}
	public double getValorRetencao() {
		return valorRetencao;
	}
	public void setValorRetencao(double valorRetencao) {
		this.valorRetencao = valorRetencao;
	}
	public double getValorReceber() {
		return valorReceber;
	}
	public void setValorReceber(double valorReceber) {
		this.valorReceber = valorReceber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIdFormaPagamento() {
		return idFormaPagamento;
	}
	public void setIdFormaPagamento(int idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}
	@Override
	public String toString() {
		return "ItemFinanceiroDTO [numSeq=" + numSeq + ", idPedido=" + idPedido + ", nomeCliente=" + nomeCliente
				+ ", telefone=" + telefone + ", numParcela=" + numParcela + ", totalParcelas=" + totalParcelas
				+ ", dataVencimento=" + dataVencimento + ", valorBruto=" + valorBruto + ", idFormaPagamento="
				+ idFormaPagamento + ", formaPagamento=" + formaPagamento + ", percentRetencao=" + percentRetencao
				+ ", valorRetencao=" + valorRetencao + ", valorReceber=" + valorReceber + ", status=" + status + "]";
	}
	 
}
