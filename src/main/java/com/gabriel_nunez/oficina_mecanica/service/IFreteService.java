package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.Frete;

public interface IFreteService {
    
    public ArrayList<Frete> recuperarTodos();
	public ArrayList<Frete> recuperarDisponiveis();
	public Frete atualizarFrete(Frete frete);
	public Frete inserirFrete(Frete novo);
	public Frete recuperarPeloPrefixo(String prefixo);
	public Frete recuperarPeloId(int id);
}
