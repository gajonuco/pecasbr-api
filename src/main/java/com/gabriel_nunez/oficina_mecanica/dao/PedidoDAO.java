/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.PedidoDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO
 *  com.gabriel_nunez.oficina_mecanica.model.Cliente
 *  com.gabriel_nunez.oficina_mecanica.model.Pedido
 *  org.springframework.data.jpa.repository.Query
 *  org.springframework.data.repository.CrudRepository
 *  org.springframework.data.repository.query.Param
 */
package com.gabriel_nunez.oficina_mecanica.dao;

import com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PedidoDAO
extends CrudRepository<Pedido, Integer> {
    public ArrayList<Pedido> findAllByCliente(Cliente var1);

    public ArrayList<Pedido> findAllByStatusOrderByDataPedidoDesc(int var1);

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.itensPedido ip LEFT JOIN FETCH ip.peca peca LEFT JOIN FETCH peca.categoriaPeca LEFT JOIN FETCH p.cliente WHERE p.status != 6 AND p.status != 7 ORDER BY p.id DESC, p.dataPedido DESC")
    public ArrayList<Pedido> findAllByStatusNotOrderByDataPedidoDesc();

    public ArrayList<Pedido> findAllByOrderByDataPedidoDesc();

    public ArrayList<Pedido> findAllByStatusInOrderByIdDesc(Collection<Integer> var1);

    public ArrayList<Pedido> findAllByClienteInOrderByIdDesc(Collection<Cliente> var1);

    public ArrayList<Pedido> findAllByClienteInAndStatusInOrderByIdDesc(Collection<Cliente> var1, Collection<Integer> var2);

    public ArrayList<Pedido> findAllByDataPedidoBetweenOrderByIdDesc(LocalDate var1, LocalDate var2);

    public ArrayList<Pedido> findAllByDataPedidoBetweenAndStatusInOrderByIdDesc(LocalDate var1, LocalDate var2, Collection<Integer> var3);

    public ArrayList<Pedido> findAllByDataPedidoBetweenAndClienteInOrderByIdDesc(LocalDate var1, LocalDate var2, Collection<Cliente> var3);

    public ArrayList<Pedido> findAllByDataPedidoBetweenAndClienteInAndStatusInOrderByIdDesc(LocalDate var1, LocalDate var2, Collection<Cliente> var3, Collection<Integer> var4);

    public ArrayList<Pedido> findAllByStatusInOrderByDataPedidoDesc(Collection<Integer> var1);

    public ArrayList<Pedido> findAllByClienteIn(Collection<Cliente> var1);

    public ArrayList<Pedido> findAllByClienteInAndStatusIn(Collection<Cliente> var1, Collection<Integer> var2);

    public ArrayList<Pedido> findAllByDataPedidoBetween(LocalDate var1, LocalDate var2);

    @Query("SELECT new com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO(SUM(p.valorTotal), p.dataPedido) FROM Pedido p WHERE p.dataPedido BETWEEN :inicio AND :fim GROUP BY p.dataPedido ORDER BY p.dataPedido ASC")
    public ArrayList<VendasPorDataDTO> recuperarVendasPorData(@Param("inicio") LocalDate var1, @Param("fim") LocalDate var2);

    public Optional<Pedido> findByAsaasPaymentId(String var1);
}

