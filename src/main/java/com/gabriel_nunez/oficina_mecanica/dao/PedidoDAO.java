package com.gabriel_nunez.oficina_mecanica.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;

public interface PedidoDAO extends CrudRepository<Pedido, Integer>{
    
    public ArrayList<Pedido> findAllByCliente(Cliente cliente);
    public ArrayList<Pedido> findAllByDataPedidoBetween(LocalDate inicio, LocalDate fim);
    public ArrayList<Pedido> findAllByStatusOrderByDataPedidoDesc(int status);
    public ArrayList<Pedido> findAllByOrderByDataPedidoDesc();
}
