/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  org.springframework.data.repository.CrudRepository
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaPecaDAO
extends CrudRepository<CategoriaPeca, Integer> {
    public ArrayList<CategoriaPeca> findByNomeContaining(String var1);

    public ArrayList<CategoriaPeca> findAllByOrderById();
}

