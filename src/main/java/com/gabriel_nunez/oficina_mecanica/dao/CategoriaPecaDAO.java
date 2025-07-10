package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;

public interface CategoriaPecaDAO extends CrudRepository<CategoriaPeca, Integer> {

    public ArrayList<CategoriaPeca> findByNomeContaining(String palavra);
    
}
