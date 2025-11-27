package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO;
import com.gabriel_nunez.oficina_mecanica.dto.FiltroPedidoDTO;

import com.gabriel_nunez.oficina_mecanica.model.Pedido;

public interface IPedidoService {

    public Pedido inserirPedido(Pedido novo);
    public Pedido atualizarPedido(Pedido pedido);
    public ArrayList<Pedido> buscarNaoCancelados();
    public ArrayList<Pedido> buscarStatus(int status);    
    public Pedido mudarStatus(int idPedido, int novoStatus);
    public ArrayList<Pedido> buscarPorPeriodo(LocalDate inicio, LocalDate fim);
    public ArrayList<Pedido> buscarTodos();
    public Pedido buscarPeloId(int id);


    public ArrayList<Pedido> filtrarPorVariosCriterios(FiltroPedidoDTO filtro);
    public List<VendasPorDataDTO> recuperarTotaisUltimaSemana(LocalDate inicio, LocalDate fim);
}
