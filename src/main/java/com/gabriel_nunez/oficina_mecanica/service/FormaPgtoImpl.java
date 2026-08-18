/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.FormaPgtoDAO
 *  com.gabriel_nunez.oficina_mecanica.model.FormaPagamento
 *  com.gabriel_nunez.oficina_mecanica.service.FormaPgtoImpl
 *  com.gabriel_nunez.oficina_mecanica.service.IFormaPgtoService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.FormaPgtoDAO;
import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import com.gabriel_nunez.oficina_mecanica.service.IFormaPgtoService;
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

