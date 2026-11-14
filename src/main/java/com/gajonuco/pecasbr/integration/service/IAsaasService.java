/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.integration.dto.DTOResponse
 *  com.gajonuco.pecasbr.integration.service.IAsaasService
 *  com.gajonuco.pecasbr.model.Cliente
 */
package com.gajonuco.pecasbr.integration.service;

import com.gajonuco.pecasbr.integration.dto.DTOResponse;
import com.gajonuco.pecasbr.model.Cliente;

import java.math.BigDecimal;

public interface IAsaasService {
    public DTOResponse createPaymentLink(BigDecimal var1, Cliente var2, Integer var3);
}

