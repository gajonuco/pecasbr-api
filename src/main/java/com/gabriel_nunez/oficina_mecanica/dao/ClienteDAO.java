package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Integer>{
    
    public Cliente findByEmailOrTelefone(String email, String telefone);
    public Cliente findByTelefone(String telefone);
    public Cliente findByCpf(String cpf);
    public ArrayList<Cliente> findByNomeStartsWith(String prefixo);
    public ArrayList<Cliente> findByNomeContaining(String keyword);
    public ArrayList<Cliente> findAllByOrderByNomeAsc();
}
