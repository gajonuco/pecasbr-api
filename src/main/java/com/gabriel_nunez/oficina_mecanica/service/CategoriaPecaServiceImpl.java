/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.service.CategoriaPecaServiceImpl
 *  com.gabriel_nunez.oficina_mecanica.service.ICategoriaPecaService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.service.ICategoriaPecaService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaPecaServiceImpl
implements ICategoriaPecaService {
    @Autowired
    private CategoriaPecaDAO dao;

    public CategoriaPeca adicionarNovaCategoriaPeca(CategoriaPeca categoriaPeca) {
        try {
            if (categoriaPeca.getNome() != null && categoriaPeca.getNome().trim().length() > 0) {
                this.dao.save(categoriaPeca);
                return categoriaPeca;
            }
        }
        catch (IllegalArgumentException ex) {
            System.out.println("DEBUG =" + ex.getMessage());
        }
        catch (Exception ex) {
            System.out.println("DEBUG = " + ex.getMessage());
        }
        return null;
    }

    public CategoriaPeca alterarCategoriaPeca(CategoriaPeca categoriaPeca) {
        try {
            if (categoriaPeca.getId() != null && categoriaPeca.getNome().trim().length() > 0) {
                return (CategoriaPeca)this.dao.save(categoriaPeca);
            }
        }
        catch (Exception ex) {
            System.out.println("DEBUG = " + String.valueOf(ex));
        }
        return null;
    }

    public ArrayList<CategoriaPeca> recuperarTodasCategoriasPecas() {
        return (ArrayList)this.dao.findAll();
    }

    public ArrayList<CategoriaPeca> recuperarPorPalavraChave(String palavraChave) {
        if (palavraChave != null && !palavraChave.trim().isEmpty()) {
            return this.dao.findByNomeContaining(palavraChave);
        }
        return new ArrayList<CategoriaPeca>();
    }

    public CategoriaPeca recuperaPorID(int id) {
        return this.dao.findById(id).orElse(null);
    }

    public ArrayList<CategoriaPeca> recuperarTodasPeloId() {
        return this.dao.findAllByOrderById();
    }
}

