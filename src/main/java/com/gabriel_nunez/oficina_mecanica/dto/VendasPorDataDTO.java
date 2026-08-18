/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO
 */
package com.gabriel_nunez.oficina_mecanica.dto;

import java.time.LocalDate;

public class VendasPorDataDTO {
    private Double total;
    private LocalDate data;

    public VendasPorDataDTO(Double total, LocalDate data) {
        this.total = total;
        this.data = data;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

