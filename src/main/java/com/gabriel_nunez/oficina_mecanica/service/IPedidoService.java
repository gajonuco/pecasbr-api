package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.Pedido;

public interface IPedidoService {

    public Pedido inserirPedido(Pedido novo);
    public ArrayList<Pedido> buscarStatus(int status);    
    public Pedido mudarStatus(int idPedido, int novoStatus);
    public ArrayList<Pedido> buscarPorPeriodo(LocalDate inicio, LocalDate fim);
    public ArrayList<Pedido> buscarTodos();
}
