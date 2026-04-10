/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.model.Frete
 *  com.gabriel_nunez.oficina_mecanica.service.IFreteService
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.model.Frete;
import java.util.ArrayList;

public interface IFreteService {
    public ArrayList<Frete> recuperarTodos();

    public ArrayList<Frete> recuperarDisponiveis();

    public Frete atualizarFrete(Frete var1);

    public Frete inserirFrete(Frete var1);

    public Frete recuperarPeloPrefixo(String var1);

    public Frete recuperarPeloId(int var1);
}

