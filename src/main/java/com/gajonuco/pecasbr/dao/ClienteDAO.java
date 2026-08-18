/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dao.ClienteDAO
 *  com.gajonuco.pecasbr.dto.CompradorDTO
 *  com.gajonuco.pecasbr.model.Cliente
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.CrudRepository
 *  org.springframework.data.repository.query.Param
 */
package com.gajonuco.pecasbr.dao;

import com.gajonuco.pecasbr.dto.CompradorDTO;
import com.gajonuco.pecasbr.model.Cliente;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClienteDAO
extends CrudRepository<Cliente, Integer> {
    public Cliente findByEmailOrTelefone(String var1, String var2);

    @Query("SELECT c FROM Cliente c WHERE REGEXP_REPLACE(c.telefone, '[^0-9]', '') = :telefone")
    public Cliente findByTelefone(@Param("telefone") String var1);

    public Cliente findByCpf(String var1);

    public ArrayList<Cliente> findByNomeStartsWith(String var1);

    public ArrayList<Cliente> findByNomeContaining(String var1);

    public ArrayList<Cliente> findAllByOrderByNomeAsc();

    @Query("SELECT DISTINCT new com.gajonuco.pecasbr.dto.CompradorDTO(cli.nome, cli.email, cli.telefone) FROM Cliente cli INNER JOIN Pedido ped ON cli.id = ped.cliente.id  INNER JOIN  ItemPedido itm ON itm.pedido.id = ped.id INNER JOIN  Peca pec ON itm.peca.id = pec.id  WHERE pec.id = :id ")
    public ArrayList<CompradorDTO> recuperarCompradores(@Param("id") int var1);

    @Query("SELECT new com.gajonuco.pecasbr.model.Cliente(cli.nome, cli.dataNasc, cli.telefone) from Cliente cli WHERE month(cli.dataNasc) = :mes ORDER BY day(cli.dataNasc)")
    public ArrayList<Cliente> recuperarAniversariante(@Param("mes") int var1);
}

