/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOResponse
 *  com.gabriel_nunez.oficina_mecanica.integration.service.IAsaasService
 *  com.gabriel_nunez.oficina_mecanica.model.Cliente
 */
package com.gabriel_nunez.oficina_mecanica.integration.service;

import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOResponse;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public interface IAsaasService {
    public DTOResponse createPaymentLink(Double var1, Cliente var2, Integer var3);
}

