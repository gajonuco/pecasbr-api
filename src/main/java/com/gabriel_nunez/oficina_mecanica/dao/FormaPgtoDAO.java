/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.FormaPgtoDAO
 *  com.gabriel_nunez.oficina_mecanica.model.FormaPagamento
 *  org.springframework.data.repository.CrudRepository
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

public interface FormaPgtoDAO
extends CrudRepository<FormaPagamento, Integer> {
    public ArrayList<FormaPagamento> findAllByVisivel(int var1);
}

