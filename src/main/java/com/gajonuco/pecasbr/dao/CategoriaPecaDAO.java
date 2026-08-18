/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.CategoriaPecaDAO
 *  com.gajonuco.pecasbr.model.CategoriaPeca
 *  org.springframework.data.repository.CrudRepository
 */
package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.model.CategoriaPeca;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaPecaDAO
extends CrudRepository<CategoriaPeca, Integer> {
    public ArrayList<CategoriaPeca> findByNomeContaining(String var1);

    public ArrayList<CategoriaPeca> findAllByOrderById();
}

