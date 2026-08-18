/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.model.CategoriaPeca
 *  com.gajonuco.pecasbr.service.ICategoriaPecaService
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.model.CategoriaPeca;
import java.util.ArrayList;

public interface ICategoriaPecaService {
    public CategoriaPeca adicionarNovaCategoriaPeca(CategoriaPeca var1);

    public CategoriaPeca alterarCategoriaPeca(CategoriaPeca var1);

    public ArrayList<CategoriaPeca> recuperarTodasCategoriasPecas();

    public ArrayList<CategoriaPeca> recuperarPorPalavraChave(String var1);

    public CategoriaPeca recuperaPorID(int var1);

    public ArrayList<CategoriaPeca> recuperarTodasPeloId();
}

