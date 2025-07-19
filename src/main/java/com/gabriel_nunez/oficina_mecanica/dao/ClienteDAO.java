package com.gabriel_nunez.oficina_mecanica.dao;

import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Integer>{
    
    public Cliente findByEmailOrTelefone(String email, String telefone);
}
