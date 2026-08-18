/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.ClienteDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO
 *  com.gabriel_nunez.oficina_mecanica.model.Cliente
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.CrudRepository
 *  org.springframework.data.repository.query.Param
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
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

    @Query("SELECT DISTINCT new com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO(cli.nome, cli.email, cli.telefone) FROM Cliente cli INNER JOIN Pedido ped ON cli.id = ped.cliente.id  INNER JOIN  ItemPedido itm ON itm.pedido.id = ped.id INNER JOIN  Peca pec ON itm.peca.id = pec.id  WHERE pec.id = :id ")
    public ArrayList<CompradorDTO> recuperarCompradores(@Param("id") int var1);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.model.Cliente(cli.nome, cli.dataNasc, cli.telefone) from Cliente cli WHERE month(cli.dataNasc) = :mes ORDER BY day(cli.dataNasc)")
    public ArrayList<Cliente> recuperarAniversariante(@Param("mes") int var1);
}

