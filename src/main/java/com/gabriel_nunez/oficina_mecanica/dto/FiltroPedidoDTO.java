package com.gabriel_nunez.oficina_mecanica.dto;

import java.time.LocalDate;

public class FiltroPedidoDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String nome;
    private int pago;
    private int entregue;
    private int cancelado;

    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
    public LocalDate getDataFim() {
        return dataFim;
    }
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getPago() {
        return pago;
    }
    public void setPago(int pago) {
        this.pago = pago;
    }
    public int getEntregue() {
        return entregue;
    }
    public void setEntregue(int entregue) {
        this.entregue = entregue;
    }
    public int getCancelado() {
        return cancelado;
    }
    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }
}
