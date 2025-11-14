package com.gabriel_nunez.oficina_mecanica.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.FormaPgtoDAO;
import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;



@Component
public class FormaPgtoImpl implements IFormaPgtoService {

	@Autowired
	private FormaPgtoDAO dao;
	
	@Override
	public ArrayList<FormaPagamento> buscarTodas() {
		return (ArrayList<FormaPagamento>)dao.findAll();
	}

	@Override
	public ArrayList<FormaPagamento> buscarVisiveis() {
		// TODO Auto-generated method stub
		return dao.findAllByVisivel(1);
	}

	@Override
	public FormaPagamento buscarPeloId(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElse(null);
	}

	@Override
	public FormaPagamento atualizar(FormaPagamento novo) {
		// TODO Auto-generated method stub
		return dao.save(novo);
	}

}
    

