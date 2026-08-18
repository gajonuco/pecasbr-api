/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.model.Frete
 *  com.gajonuco.pecasbr.service.IFreteService
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.model.Frete;
import java.util.ArrayList;

public interface IFreteService {
    public ArrayList<Frete> recuperarTodos();

    public ArrayList<Frete> recuperarDisponiveis();

    public Frete atualizarFrete(Frete var1);

    public Frete inserirFrete(Frete var1);

    public Frete recuperarPeloPrefixo(String var1);

    public Frete recuperarPeloId(int var1);
}

