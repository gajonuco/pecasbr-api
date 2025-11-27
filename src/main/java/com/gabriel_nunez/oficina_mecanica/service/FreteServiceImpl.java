package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.FreteDAO;
import com.gabriel_nunez.oficina_mecanica.model.Frete;

@Component
public class FreteServiceImpl implements IFreteService {

	@Autowired
	private FreteDAO dao;
	
	@Override
	public ArrayList<Frete> recuperarTodos() {
		// TODO Auto-generated method stub
		return (ArrayList<Frete>)dao.findAll();
	}

	@Override
	public ArrayList<Frete> recuperarDisponiveis() {
		// TODO Auto-generated method stub
		return dao.findAllByDisponivelOrderByPrefixoDesc(1);
	}

	@Override
	public Frete atualizarFrete(Frete frete) {
		// TODO Auto-generated method stub
		return dao.save(frete);
	}

	@Override
	public Frete inserirFrete(Frete novo) {
		// TODO Auto-generated method stub
		return dao.save(novo);
	}

	@Override
	public Frete recuperarPeloPrefixo(String prefixo) {
		
		// qual vai ser o critério? vou inicialmente buscar todos
		ArrayList<Frete> listaFretes = dao.findAllByDisponivelOrderByPrefixoDesc(1);
		//System.out.println(listaFretes);
		Frete frete = new Frete();
		for (Frete f: listaFretes) {
			if (prefixo.startsWith(f.getPrefixo())) {
				return f;
			}
		}
		return null;
	}

	@Override
	public Frete recuperarPeloId(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElse(null);
	}
}