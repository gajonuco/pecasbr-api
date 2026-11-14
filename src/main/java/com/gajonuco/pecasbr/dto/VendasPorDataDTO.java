/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.VendasPorDataDTO
 */
package com.gajonuco.pecasbr.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VendasPorDataDTO {
    private BigDecimal total;
    private LocalDate data;

    public VendasPorDataDTO(BigDecimal total, LocalDate data) {
        this.total = total;
        this.data = data;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

