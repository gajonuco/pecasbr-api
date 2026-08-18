/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.FormaPgtoDAO
 *  com.gajonuco.pecasbr.model.FormaPagamento
 *  org.springframework.data.repository.CrudRepository
 */
package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.model.FormaPagamento;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface FormaPgtoDAO
extends CrudRepository<FormaPagamento, Integer> {
    public ArrayList<FormaPagamento> findAllByVisivel(int var1);
}

