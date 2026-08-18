/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.RegistroFinanceiroDAO
 *  com.gajonuco.pecasbr.dto.ItemFinanceiroDTO
 *  com.gajonuco.pecasbr.model.RegistroFinanceiro
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.CrudRepository
 */
package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.dto.ItemFinanceiroDTO;
import com.gajonuco.pecasbr.model.RegistroFinanceiro;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RegistroFinanceiroDAO
extends CrudRepository<RegistroFinanceiro, Integer> {
    @Query("SELECT new  com.gajonuco.pecasbr.dto.ItemFinanceiroDTO(fin.numSeq, ped.id, cli.nome, cli.telefone,  fin.numParcela, fin.totalParcelas,  fin.vencimento, fin.valorBruto, pgt.numSeq,  pgt.descricao, fin.percentRetencao,  fin.valorRetencao, fin.valorLiquido, fin.status)  FROM RegistroFinanceiro fin INNER JOIN Pedido ped ON fin.pedido.id = ped.id INNER JOIN Cliente cli on ped.cliente.id = cli.id  INNER JOIN FormaPagamento pgt on pgt.numSeq = fin.forma.numSeq  WHERE fin.status != -1")
    public ArrayList<ItemFinanceiroDTO> recuperarItensFinanceiros();
}

