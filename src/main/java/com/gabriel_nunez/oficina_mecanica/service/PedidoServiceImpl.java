package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.ClienteDAO;
import com.gabriel_nunez.oficina_mecanica.dao.PedidoDAO;
import com.gabriel_nunez.oficina_mecanica.dto.VendasPorDataDTO;
import com.gabriel_nunez.oficina_mecanica.dto.FiltroPedidoDTO;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.ItemPedido;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;

@Component
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private PedidoDAO dao;

    @Autowired
    private ClienteDAO clienteDao;

    @Override
    public Pedido inserirPedido(Pedido novo) {
        // TODO Auto-generated method stub
        try {
            double total = 0.0;
            for (ItemPedido item: novo.getItensPedido()) {
                item.setPrecoUnitario(item.getPeca().getPreco());
                item.setPrecoTotal(item.getPrecoUnitario() * item.getQtdtItem());
                /*if(item.getQtdtItem() >= 5) { // vou dar 20% de desconto
                    item.setPrecoTotal(item.getPrecoUnitario() * item.getQtdtItem() * 0.8);
                } else {
                    item.setPrecoTotal(item.getPrecoUnitario() * item.getQtdtItem());
                }*/

                total += item.getPrecoTotal();
            }

            for(ItemPedido item: novo.getItensPedido()){
                item.setPedido(novo);
            }
            novo.setStatus(Pedido.NOVO_PEDIDO);
            novo.setValorTotal(total);
            dao.save(novo);
            return novo;
        } catch (Exception ex) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public ArrayList<Pedido> buscarStatus(int status) {
        // TODO Auto-generated method stub
        return dao.findAllByStatusOrderByDataPedidoDesc(status);
    }

    @Override
    public Pedido mudarStatus(int idPedido, int novoStatus) {
        try {
            Pedido pedido = dao.findById(idPedido).get();
            pedido.setStatus(novoStatus);
            dao.save(pedido);
            return pedido;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<Pedido> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        // TODO Auto-generated method stub
       return dao.findAllByDataPedidoBetween(inicio, fim);
    }

    @Override
    public ArrayList<Pedido> buscarTodos() {
        // TODO Auto-generated method stub
        return dao.findAllByOrderByDataPedidoDesc();
    }

    @Override
    public List<VendasPorDataDTO> recuperarTotaisUltimaSemana(LocalDate inicio, LocalDate fim) {
        ArrayList<VendasPorDataDTO> lista = dao.recuperarVendasPorData(inicio, fim);
        return lista;
    } 


    @Override
    public ArrayList<Pedido> filtrarPorVariosCriterios(FiltroPedidoDTO filtro) {
        // TODO Auto-generated method stub
        boolean temData = filtro.getDataInicio() != null && filtro.getDataFim() != null;
        boolean temNome = filtro.getNome() != null && !filtro.getNome().trim().isEmpty();
        boolean temStatus = filtro.getCancelado() != 0 || filtro.getEntregue() != 0 || filtro.getPago() != 0 || filtro.getNovo() != 0 || filtro.getTransporte() != 0 || filtro.getPosVenda() != 0 || filtro.getFinalizado() != 0;
        System.out.println("cancelado = " + filtro.getCancelado());
        if(!temData && !temNome && !temStatus){
            System.out.println("primeira condição");
            return dao.findAllByOrderByDataPedidoDesc();
        }
        
        else if(!temData && temNome && !temStatus){

            ArrayList<Cliente> clientes = clienteDao.findByNomeContaining(filtro.getNome());
            return dao.findAllByClienteIn(clientes);
        }

        else if(!temData && !temNome && temStatus){

            System.out.println("segunda condição");
            return dao.findAllByStatusInOrderByIdDesc(this.getStatus(filtro));
        }
        else if(!temData && temNome && temStatus){

            System.out.println("terceira condição");
            ArrayList<Cliente> clientes = clienteDao.findByNomeContaining(filtro.getNome());
            return dao.findAllByClienteInAndStatusIn(clientes, this.getStatus(filtro));
        }
        else if(temData && !temNome && !temStatus){

            System.out.println("quarta condição");
            return dao.findAllByDataPedidoBetweenOrderByIdDesc(filtro.getDataInicio(),filtro.getDataFim());
        }
        else if(temData && !temNome && temStatus){

            System.out.println("quinta condição");
            return dao.findAllByDataPedidoBetweenAndStatusInOrderByIdDesc(filtro.getDataInicio(), filtro.getDataFim(), this.getStatus(filtro));
        }
        else if(temData && temNome && !temStatus){

            System.out.println("sexta condição");
            ArrayList<Cliente> clientes = clienteDao.findByNomeContaining(filtro.getNome());
            return dao.findAllByDataPedidoBetweenAndClienteInOrderByIdDesc(filtro.getDataInicio(), filtro.getDataFim(),  clientes);
        }

        else if(temData && temNome && temStatus){

            System.out.println("séptima condição");
            ArrayList<Cliente> clientes = clienteDao.findByNomeContaining(filtro.getNome());
            return dao.findAllByDataPedidoBetweenAndClienteInAndStatusInOrderByIdDesc(filtro.getDataInicio(), filtro.getDataFim(), clientes, this.getStatus(filtro));
        }

        return null;
    }


    private Collection<Integer> getStatus(FiltroPedidoDTO filtro){
        Collection<Integer> status = new ArrayList<Integer>();
		if (filtro.getPago() != 0) status.add(Pedido.PAGO);
		if (filtro.getCancelado() != 0) status.add(Pedido.CANCELADO);
		if (filtro.getEntregue() != 0) status.add(Pedido.ENTREGUE);
		if (filtro.getNovo() != 0) status.add(Pedido.NOVO_PEDIDO);
		if (filtro.getTransporte() != 0) status.add(Pedido.EM_TRANSPORTE);
		if (filtro.getPosVenda() != 0 )status.add(Pedido.POS_VENDA);
		if (filtro.getFinalizado() != 0) status.add(Pedido.FINALIZADO);

        return status;
    }

    @Override
    public Pedido buscarPeloId(int id) {
        // TODO Auto-generated method stub
        return dao.findById(id).get();
    }  
    
}
