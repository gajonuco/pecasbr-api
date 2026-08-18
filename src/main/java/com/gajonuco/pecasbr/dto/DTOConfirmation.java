/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.DTOConfirmation
 *  com.gajonuco.pecasbr.dto.Payment
 */
package com.gajonuco.pecasbr.dto;

import com.gajonuco.pecasbr.dto.Payment;

public class DTOConfirmation {
    private String id;
    private String event;
    private Payment payment;

    public Payment getPayment() {
        return this.payment;
    }

    public String getId() {
        return this.id;
    }

    public String getEvent() {
        return this.event;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}

