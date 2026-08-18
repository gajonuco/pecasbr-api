/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.FreteDAO
 *  com.gajonuco.pecasbr.model.Frete
 *  org.springframework.data.repository.CrudRepository
 */
package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.model.Frete;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface FreteDAO
extends CrudRepository<Frete, Integer> {
    public ArrayList<Frete> findAllByDisponivelOrderByPrefixoDesc(int var1);

    public Frete findByPrefixo(String var1);
}

