/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.FiltroRankingProdutosDTO
 */
package com.gajonuco.pecasbr.dto;

import java.time.LocalDate;

public class FiltroRankingProdutosDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int limiteProdutos = 6;
    private String ordenarPor = "quantidade";

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

    public int getLimiteProdutos() {
        return this.limiteProdutos;
    }

    public void setLimiteProdutos(int limiteProdutos) {
        if (limiteProdutos >= 2 && limiteProdutos <= 6) {
            this.limiteProdutos = limiteProdutos;
        }
    }

    public String getOrdenarPor() {
        return this.ordenarPor;
    }

    public void setOrdenarPor(String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }
}

