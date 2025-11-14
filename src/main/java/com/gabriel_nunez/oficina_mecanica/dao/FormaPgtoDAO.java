package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;


public interface FormaPgtoDAO extends CrudRepository<FormaPagamento, Integer>{
	public ArrayList<FormaPagamento> findAllByVisivel(int visivel);

}