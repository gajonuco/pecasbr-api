/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.ItemFinanceiroDTO
 *  com.gajonuco.pecasbr.dto.RegistroFinanceiroDTO
 *  com.gajonuco.pecasbr.model.RegistroFinanceiro
 *  com.gajonuco.pecasbr.service.IFluxoFinanceiroService
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.ItemFinanceiroDTO;
import com.gajonuco.pecasbr.dto.RegistroFinanceiroDTO;
import com.gajonuco.pecasbr.model.RegistroFinanceiro;
import java.util.ArrayList;

public interface IFluxoFinanceiroService {
    public int gerarFluxoFinanceiro(RegistroFinanceiroDTO var1);

    public ArrayList<ItemFinanceiroDTO> recuperarRegistros();

    public RegistroFinanceiro alterarStatus(ItemFinanceiroDTO var1);
}

