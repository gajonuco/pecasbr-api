/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.UsuarioDAO
 *  com.gajonuco.pecasbr.model.Usuario
 *  org.springframework.data.repository.CrudRepository
 */
package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDAO
extends CrudRepository<Usuario, Integer> {
    public Usuario findByUsernameOrEmail(String var1, String var2);
}

