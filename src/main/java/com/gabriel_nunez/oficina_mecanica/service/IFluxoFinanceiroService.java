package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;

public interface IFluxoFinanceiroService {

	public int gerarFluxoFinanceiro(RegistroFinanceiroDTO registro);
	public ArrayList<ItemFinanceiroDTO> recuperarRegistros();
	public RegistroFinanceiro alterarStatus(ItemFinanceiroDTO item);
}
