/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.CategoriaPecaDAO
 *  com.gajonuco.pecasbr.model.CategoriaPeca
 *  com.gajonuco.pecasbr.service.CategoriaPecaServiceImpl
 *  com.gajonuco.pecasbr.service.ICategoriaPecaService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.CategoriaPecaDAO;
import com.gajonuco.pecasbr.model.CategoriaPeca;
import com.gajonuco.pecasbr.service.ICategoriaPecaService;
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

