package com.gabriel_nunez.oficina_mecanica.dto;

import java.time.LocalDate;

public class FiltroRankingProdutosDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int limiteProdutos = 6;
     private String ordenarPor = "quantidade"; 

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

    public int getLimiteProdutos() {
        return limiteProdutos;
    }

    public void setLimiteProdutos(int limiteProdutos) {

        if (limiteProdutos >= 2 && limiteProdutos <= 6) {
            this.limiteProdutos = limiteProdutos;

        }
    }

        public String getOrdenarPor() {
        return ordenarPor;
    }

    public void setOrdenarPor(String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

}
