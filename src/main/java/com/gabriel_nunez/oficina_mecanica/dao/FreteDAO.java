/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.FreteDAO
 *  com.gabriel_nunez.oficina_mecanica.model.Frete
 *  org.springframework.data.repository.CrudRepository
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.model.Frete;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface FreteDAO
extends CrudRepository<Frete, Integer> {
    public ArrayList<Frete> findAllByDisponivelOrderByPrefixoDesc(int var1);

    public Frete findByPrefixo(String var1);
}

