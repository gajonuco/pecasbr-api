/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO
 *  com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro
 *  com.gabriel_nunez.oficina_mecanica.service.IFluxoFinanceiroService
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;
import java.util.ArrayList;

public interface IFluxoFinanceiroService {
    public int gerarFluxoFinanceiro(RegistroFinanceiroDTO var1);

    public ArrayList<ItemFinanceiroDTO> recuperarRegistros();

    public RegistroFinanceiro alterarStatus(ItemFinanceiroDTO var1);
}

