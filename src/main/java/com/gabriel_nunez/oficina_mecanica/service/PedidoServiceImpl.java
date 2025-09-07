package com.gabriel_nunez.oficina_mecanica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.PedidoDAO;
import com.gabriel_nunez.oficina_mecanica.model.ItemPedido;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;

@Component
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private PedidoDAO dao;

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
            novo.setValorTotal(total);
            dao.save(novo);
            return novo;
        } catch (Exception ex) {
            // TODO: handle exception
            return null;
        }
    }
    
}
