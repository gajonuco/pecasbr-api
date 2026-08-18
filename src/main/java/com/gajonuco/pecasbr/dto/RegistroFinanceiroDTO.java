/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.RegistroFinanceiroDTO
 *  com.gajonuco.pecasbr.model.FormaPagamento
 *  com.gajonuco.pecasbr.model.Pedido
 */
package com.gajonuco.pecasbr.dto;

import com.gajonuco.pecasbr.model.FormaPagamento;
import com.gajonuco.pecasbr.model.Pedido;

public class RegistroFinanceiroDTO {
    private int diaVencimento;
    private Pedido pedido;
    private FormaPagamento formaPagamento;
    private int totalParcelas;

    public int getDiaVencimento() {
        return this.diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public FormaPagamento getFormaPagamento() {
        return this.formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getTotalParcelas() {
        return this.totalParcelas;
    }

    public void setTotalParcelas(int totalParcelas) {
        this.totalParcelas = totalParcelas;
    }

    public String toString() {
        return "RegistroFinanceiroDTO [diaVencimento=" + this.diaVencimento + ", pedido=" + String.valueOf(this.pedido) + ", formaPagamento=" + String.valueOf(this.formaPagamento) + ", totalParcelas=" + this.totalParcelas + "]";
    }
}

