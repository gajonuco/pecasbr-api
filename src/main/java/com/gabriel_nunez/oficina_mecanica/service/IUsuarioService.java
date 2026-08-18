/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.model.Usuario
 *  com.gabriel_nunez.oficina_mecanica.service.IUsuarioService
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.model.Usuario;
import java.util.ArrayList;

public interface IUsuarioService {
    public Usuario recuperarUsuario(Usuario var1);

    public ArrayList<Usuario> recuperarTodos();

    public Usuario adicionarNovo(Usuario var1);

    public Usuario atualizarUsuario(Usuario var1);

    public Usuario recuerarPeloId(int var1);

    public Usuario buscarUsuarioPorCredenciais(Usuario var1);
}

