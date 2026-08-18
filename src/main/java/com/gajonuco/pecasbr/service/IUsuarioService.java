/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.model.Usuario
 *  com.gajonuco.pecasbr.service.IUsuarioService
 */
package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.model.Usuario;
import java.util.ArrayList;

public interface IUsuarioService {
    public Usuario recuperarUsuario(Usuario var1);

    public ArrayList<Usuario> recuperarTodos();

    public Usuario adicionarNovo(Usuario var1);

    public Usuario atualizarUsuario(Usuario var1);

    public Usuario recuerarPeloId(int var1);

    public Usuario buscarUsuarioPorCredenciais(Usuario var1);
}

