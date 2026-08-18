/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.FiltroPedidoDTO
 */
package com.gabriel_nunez.oficina_mecanica.dto;

import java.time.LocalDate;

public class FiltroPedidoDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String nome;
    private int novo;
    private int pago;
    private int transporte;
    private int entregue;
    private int posVenda;
    private int finalizado;
    private int cancelado;

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPago() {
        return this.pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public int getEntregue() {
        return this.entregue;
    }

    public void setEntregue(int entregue) {
        this.entregue = entregue;
    }

    public int getCancelado() {
        return this.cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    public int getNovo() {
        return this.novo;
    }

    public void setNovo(int novo) {
        this.novo = novo;
    }

    public int getTransporte() {
        return this.transporte;
    }

    public void setTransporte(int transporte) {
        this.transporte = transporte;
    }

    public int getPosVenda() {
        return this.posVenda;
    }

    public void setPosVenda(int posVenda) {
        this.posVenda = posVenda;
    }

    public int getFinalizado() {
        return this.finalizado;
    }

    public void setFinalizado(int finalizado) {
        this.finalizado = finalizado;
    }
}

