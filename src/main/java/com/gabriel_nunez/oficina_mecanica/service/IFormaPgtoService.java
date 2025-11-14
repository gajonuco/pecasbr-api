package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;

public interface IFormaPgtoService {
	
	public ArrayList<FormaPagamento> buscarTodas();
	public ArrayList<FormaPagamento> buscarVisiveis();
	public FormaPagamento buscarPeloId(int id);
	public FormaPagamento atualizar(FormaPagamento novo);

}