package com.gabriel_nunez.oficina_mecanica.dao;

import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario,Integer>{

     public Usuario findByUsernameOrEmail(String username, String email);
}
