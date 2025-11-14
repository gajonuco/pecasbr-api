package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;

public interface RegistroFinanceiroDAO extends CrudRepository<RegistroFinanceiro, Integer>{
    	@Query("SELECT new "
			+ " com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO"
			+ "(fin.numSeq, ped.id, cli.nome, cli.telefone, "
			+ " fin.numParcela, fin.totalParcelas,  fin.vencimento,"
			+ " fin.valorBruto, pgt.numSeq,  pgt.descricao, fin.percentRetencao, "
			+ " fin.valorRetencao, fin.valorLiquido, fin.status) "
			+ " FROM RegistroFinanceiro fin INNER JOIN Pedido ped ON fin.pedido.id = ped.id"
			+ " INNER JOIN Cliente cli on ped.cliente.id = cli.id "
			+ " INNER JOIN FormaPagamento pgt on pgt.numSeq = fin.forma.numSeq "
			+ " WHERE fin.status != -1")
	public ArrayList<ItemFinanceiroDTO> recuperarItensFinanceiros();
}
