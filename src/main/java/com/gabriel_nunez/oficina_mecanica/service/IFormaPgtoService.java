/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.model.FormaPagamento
 *  com.gabriel_nunez.oficina_mecanica.service.IFormaPgtoService
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import java.util.ArrayList;

public interface IFormaPgtoService {
    public ArrayList<FormaPagamento> buscarTodas();

    public ArrayList<FormaPagamento> buscarVisiveis();

    public FormaPagamento buscarPeloId(int var1);

    public FormaPagamento atualizar(FormaPagamento var1);
}

