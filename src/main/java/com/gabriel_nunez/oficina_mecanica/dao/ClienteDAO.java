package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Integer>{
    
    public Cliente findByEmailOrTelefone(String email, String telefone);
    public Cliente findByTelefone(String telefone);
    public Cliente findByCpf(String cpf);
    public ArrayList<Cliente> findByNomeStartsWith(String prefixo);
    public ArrayList<Cliente> findByNomeContaining(String keyword);
    public ArrayList<Cliente> findAllByOrderByNomeAsc();

    
    @Query("SELECT DISTINCT new com.gabriel_nunez.oficina_mecanica.dto.CompradorDTO(cli.nome, cli.email, cli.telefone)"
            + " FROM Cliente cli INNER JOIN Pedido ped ON cli.id = ped.cliente.id "
            + " INNER JOIN "
            + " ItemPedido itm ON itm.pedido.id = ped.id"
            + " INNER JOIN "
            + " Peca pec ON itm.peca.id = pec.id "
            + " WHERE pec.id = :id ")
    public ArrayList<CompradorDTO> recuperarCompradores(@Param("id") int idPeca);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.model.Cliente(cli.nome, cli.dataNasc, cli.telefone)"
                   + " from Cliente cli WHERE month(cli.dataNasc) = :mes ORDER BY day(cli.dataNasc)")
    public ArrayList <Cliente> recuperarAniversariante(@Param("mes") int mes);
}
