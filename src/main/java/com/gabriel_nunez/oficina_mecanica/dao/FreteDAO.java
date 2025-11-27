package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.Frete;

public interface FreteDAO extends CrudRepository<Frete, Integer> {
	
	public ArrayList<Frete> findAllByDisponivelOrderByPrefixoDesc(int disponivel);
	public Frete findByPrefixo(String prefixo);

}