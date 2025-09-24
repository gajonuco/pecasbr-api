package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.gabriel_nunez.oficina_mecanica.model.Pedido;

public interface IPedidoService {

    public Pedido inserirPedido(Pedido novo);
    public ArrayList<Pedido> buscarStatus(int status);    
    public Pedido mudarStatus(Pedido pedido, int novoStatus);
    public ArrayList<Pedido> buscarPorPeriodo(LocalDate inicio, LocalDate fim);
}
