/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.FormaPgtoDAO
 *  com.gajonuco.pecasbr.model.FormaPagamento
 *  com.gajonuco.pecasbr.service.FormaPgtoImpl
 *  com.gajonuco.pecasbr.service.IFormaPgtoService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.FormaPgtoDAO;
import com.gajonuco.pecasbr.model.FormaPagamento;
import com.gajonuco.pecasbr.service.IFormaPgtoService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPgtoImpl
implements IFormaPgtoService {
    @Autowired
    private FormaPgtoDAO dao;

    public ArrayList<FormaPagamento> buscarTodas() {
        return (ArrayList)this.dao.findAll();
    }

    public ArrayList<FormaPagamento> buscarVisiveis() {
        return this.dao.findAllByVisivel(1);
    }

    public FormaPagamento buscarPeloId(int id) {
        return this.dao.findById(id).orElse(null);
    }

    public FormaPagamento atualizar(FormaPagamento novo) {
        return (FormaPagamento)this.dao.save(novo);
    }
}

