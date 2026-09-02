package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.model.Endereco;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnderecoDAO extends CrudRepository<Endereco,Integer> {
    List<Endereco> findByClientId(int clienteId);
}
