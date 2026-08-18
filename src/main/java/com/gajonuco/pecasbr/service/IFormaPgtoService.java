/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.model.FormaPagamento
 *  com.gajonuco.pecasbr.service.IFormaPgtoService
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.model.FormaPagamento;
import java.util.ArrayList;

public interface IFormaPgtoService {
    public ArrayList<FormaPagamento> buscarTodas();

    public ArrayList<FormaPagamento> buscarVisiveis();

    public FormaPagamento buscarPeloId(int var1);

    public FormaPagamento atualizar(FormaPagamento var1);
}

