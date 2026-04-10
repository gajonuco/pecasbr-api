/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.UsuarioDAO
 *  com.gabriel_nunez.oficina_mecanica.model.Usuario
 *  com.gabriel_nunez.oficina_mecanica.service.IUsuarioService
 *  com.gabriel_nunez.oficina_mecanica.service.UsuarioServiceImpl
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.UsuarioDAO;
import com.gabriel_nunez.oficina_mecanica.model.Usuario;
import com.gabriel_nunez.oficina_mecanica.service.IUsuarioService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioServiceImpl
implements IUsuarioService {
    @Autowired
    private UsuarioDAO dao;

    public Usuario recuperarUsuario(Usuario original) {
        Usuario user = this.dao.findByUsernameOrEmail(original.getUsername(), original.getEmail());
        if (user != null && user.getSenha().equals(original.getSenha()) && user.getAtivo() == 1) {
            user.setSenha(null);
            return user;
        }
        return null;
    }

    public Usuario buscarUsuarioPorCredenciais(Usuario original) {
        Usuario user = this.dao.findByUsernameOrEmail(original.getUsername(), original.getEmail());
        if (user != null && user.getSenha().equals(original.getSenha())) {
            user.setSenha(null);
            return user;
        }
        return null;
    }

    public ArrayList<Usuario> recuperarTodos() {
        return (ArrayList)this.dao.findAll();
    }

    public Usuario adicionarNovo(Usuario novo) {
        if (novo.getUsername().length() > 0 && novo.getEmail().length() > 0 && novo.getNome_usuario().length() > 0 && novo.getEmail().length() > 0) {
            novo.setAtivo(1);
            try {
                this.dao.save(novo);
                return novo;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Usuario atualizarUsuario(Usuario user) {
        try {
            this.dao.save(user);
            return user;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Usuario recuerarPeloId(int id) {
        return this.dao.findById(id).orElse(null);
    }
}

