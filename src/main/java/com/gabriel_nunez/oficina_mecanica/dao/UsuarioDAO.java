/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.UsuarioDAO
 *  com.gabriel_nunez.oficina_mecanica.model.Usuario
 *  org.springframework.data.repository.CrudRepository
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDAO
extends CrudRepository<Usuario, Integer> {
    public Usuario findByUsernameOrEmail(String var1, String var2);
}

