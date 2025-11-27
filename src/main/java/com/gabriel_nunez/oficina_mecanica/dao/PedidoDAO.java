package com.gabriel_nunez.oficina_mecanica.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO;

public interface PedidoDAO extends CrudRepository<Pedido, Integer> {

    public ArrayList<Pedido> findAllByCliente(Cliente cliente);

    public ArrayList<Pedido> findAllByStatusOrderByDataPedidoDesc(int status);

    /* combinacao 0 */
    // @Query("SELECT new com.gabriel_nunez.oficina_mecanica.model.Pedido(ped.id,
    // ped.dataPedido, cli.nome, ped.valorTotal, ped.valorFrete, ped.retirar,
    // ped.observacoes, ped.status)"
    // + " FROM Pedido ped INNER JOIN Cliente cli ON ped.cliente.id = cli.id"
    // + " WHERE ped.status != 6 and ped.status != 7"
    // + " ORDER BY ped.id DESC, ped.dataPedido DESC")
    // public ArrayList<Pedido> findAllByStatusNotOrderByDataPedidoDesc();

    @Query("SELECT DISTINCT p FROM Pedido p " +
            "LEFT JOIN FETCH p.itensPedido ip " +
            "LEFT JOIN FETCH ip.peca peca " +
            "LEFT JOIN FETCH peca.categoriaPeca " +
            "LEFT JOIN FETCH p.cliente " +
            "WHERE p.status != 6 AND p.status != 7 " +
            "ORDER BY p.id DESC, p.dataPedido DESC")
    ArrayList<Pedido> findAllByStatusNotOrderByDataPedidoDesc();

    public ArrayList<Pedido> findAllByOrderByDataPedidoDesc();

    public ArrayList<Pedido> findAllByStatusInOrderByIdDesc(Collection<Integer> status);

    public ArrayList<Pedido> findAllByClienteInOrderByIdDesc(Collection<Cliente> cliente);

    public ArrayList<Pedido> findAllByClienteInAndStatusInOrderByIdDesc(Collection<Cliente> cliente,
            Collection<Integer> status);

    public ArrayList<Pedido> findAllByDataPedidoBetweenOrderByIdDesc(LocalDate inicio, LocalDate fim);

    public ArrayList<Pedido> findAllByDataPedidoBetweenAndStatusInOrderByIdDesc(LocalDate inicio, LocalDate fim,
            Collection<Integer> status);

    public ArrayList<Pedido> findAllByDataPedidoBetweenAndClienteInOrderByIdDesc(LocalDate inicio, LocalDate fim,
            Collection<Cliente> cliente);

    public ArrayList<Pedido> findAllByDataPedidoBetweenAndClienteInAndStatusInOrderByIdDesc(LocalDate inicio,
            LocalDate fim, Collection<Cliente> cliente, Collection<Integer> status);

    public ArrayList<Pedido> findAllByStatusInOrderByDataPedidoDesc(Collection<Integer> status);

    public ArrayList<Pedido> findAllByClienteIn(Collection<Cliente> cliente);

    public ArrayList<Pedido> findAllByClienteInAndStatusIn(Collection<Cliente> cliente, Collection<Integer> status);

    public ArrayList<Pedido> findAllByDataPedidoBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO(SUM(p.valorTotal), p.dataPedido) " +
            "FROM Pedido p WHERE p.dataPedido BETWEEN :inicio AND :fim " +
            "GROUP BY p.dataPedido" +
            " ORDER BY p.dataPedido ASC")
    ArrayList<VendasPorDataDTO> recuperarVendasPorData(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

}
