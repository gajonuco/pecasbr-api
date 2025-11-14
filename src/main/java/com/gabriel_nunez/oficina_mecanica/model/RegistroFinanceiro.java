package com.gabriel_nunez.oficina_mecanica.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_financeiro")
public class RegistroFinanceiro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="num_seq")
	private int numSeq;
	
	@Column(name="num_parcela")
	private int numParcela;
	
	@Column(name="total_parcelas")
	private int totalParcelas;
	
	@Column(name="vencimento")
	private LocalDate vencimento;
	
	@Column(name="valor_bruto")
	private double valorBruto;
	
	@Column(name="percent_retencao")
	private double percentRetencao;
	
	@Column(name="valor_retencao")
	private double valorRetencao;
	
	@Column(name="valor_liquido")
	private double valorLiquido;
	
	@Column(name="status")
	private int    status;
	
	@ManyToOne()
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name="forma_pgto")
	private FormaPagamento forma;

	public int getNumSeq() {
		return numSeq;
	}

	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
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

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	public double getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(double valorBruto) {
		this.valorBruto = valorBruto;
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

	public double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public FormaPagamento getForma() {
		return forma;
	}

	public void setForma(FormaPagamento forma) {
		this.forma = forma;
	}

	@Override
	public String toString() {
		return "RegistroFinanceiro [numSeq=" + numSeq + ", numParcela=" + numParcela + ", totalParcelas="
				+ totalParcelas + ", vencimento=" + vencimento + ", valorBruto=" + valorBruto + ", percentRetencao="
				+ percentRetencao + ", valorRetencao=" + valorRetencao + ", valorLiquido=" + valorLiquido + ", status="
				+ status + ", pedido=" + pedido.getId() + ", forma=" + forma.getNumSeq() + "]";
	}
	
}
