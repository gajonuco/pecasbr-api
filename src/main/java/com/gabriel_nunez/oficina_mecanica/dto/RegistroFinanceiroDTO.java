package com.gabriel_nunez.oficina_mecanica.dto;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;

public class RegistroFinanceiroDTO {
    
    private int diaVencimento;
	private Pedido pedido;
	private FormaPagamento formaPagamento;
	private int totalParcelas;
	
	public int getDiaVencimento() {
		return diaVencimento;
	}
	public void setDiaVencimento(int diaVencimento) {
		this.diaVencimento = diaVencimento;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public int getTotalParcelas() {
		return totalParcelas;
	}
	public void setTotalParcelas(int totalParcelas) {
		this.totalParcelas = totalParcelas;
	}
	@Override
	public String toString() {
		return "RegistroFinanceiroDTO [diaVencimento=" + diaVencimento + ", pedido=" + pedido + ", formaPagamento="
				+ formaPagamento + ", totalParcelas=" + totalParcelas + "]";
	}
}
