package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.model.Endereco;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoDAO extends CrudRepository<Endereco,Integer> {
    List<Endereco> findByClienteId(int clienteId);

    Optional<Endereco> findByIdAndClienteId(int id,int clienteId);
}
