package com.gabriel_nunez.oficina_mecanica.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.UsuarioDAO;
import com.gabriel_nunez.oficina_mecanica.model.Usuario;

@Component
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioDAO dao;

    @Override
    public Usuario recuperarUsuario(Usuario original) {
       
        Usuario user = dao.findByUsernameOrEmail(original.getUsername(), original.getEmail());
        if(user != null){
            if(user.getSenha().equals(original.getSenha())){
                user.setSenha(null);
            }
            return user;
        }
        return null;
    }
}
