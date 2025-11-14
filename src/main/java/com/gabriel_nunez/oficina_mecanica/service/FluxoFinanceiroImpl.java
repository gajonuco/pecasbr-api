package com.gabriel_nunez.oficina_mecanica.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.RegistroFinanceiroDAO;
import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;

@Component
public class FluxoFinanceiroImpl implements IFluxoFinanceiroService {

    @Autowired
    private RegistroFinanceiroDAO dao;

    @Override
    public int gerarFluxoFinanceiro(RegistroFinanceiroDTO registro) {
        LocalDate vencimento = LocalDate.now();
        LocalDate dataParcela = LocalDate.of(vencimento.getYear(), vencimento.getMonth(), registro.getDiaVencimento());
        for (int parcela = 1; parcela <= registro.getTotalParcelas(); parcela++) {
            RegistroFinanceiro r = new RegistroFinanceiro();
            r.setForma(registro.getFormaPagamento());
            r.setNumParcela(parcela);
            r.setTotalParcelas(registro.getTotalParcelas());
            r.setPercentRetencao(registro.getFormaPagamento().getRetencao());
            r.setValorBruto(registro.getPedido().getValorTotal() / registro.getTotalParcelas());
            r.setValorRetencao(r.getValorBruto() * r.getPercentRetencao() / 100);
            r.setVencimento(dataParcela);
            dataParcela = dataParcela.plusMonths(1);
            r.setValorLiquido(registro.getPedido().getValorTotal() * 
                    (1.0 - registro.getFormaPagamento().getRetencao() / 100) / registro.getTotalParcelas());
            r.setStatus(0);
            r.setPedido(registro.getPedido());
            dao.save(r);
        }
        return registro.getTotalParcelas();
    }

    @Override
    public ArrayList<ItemFinanceiroDTO> recuperarRegistros() {
        return dao.recuperarItensFinanceiros();
    }

    @Override
    public RegistroFinanceiro alterarStatus(ItemFinanceiroDTO item) {
        try {
            RegistroFinanceiro registro = new RegistroFinanceiro();
            registro.setNumSeq(item.getNumSeq());
            registro.setNumParcela(item.getNumParcela());
            registro.setTotalParcelas(item.getTotalParcelas());
            registro.setVencimento(item.getDataVencimento());
            registro.setValorBruto(item.getValorBruto());
            registro.setPercentRetencao(item.getPercentRetencao());
            registro.setValorRetencao(item.getValorRetencao());
            registro.setValorLiquido(item.getValorReceber());
            registro.setStatus(item.getStatus());

            Pedido pedido = new Pedido();
            pedido.setId(item.getIdPedido());
            registro.setPedido(pedido);

            FormaPagamento forma = new FormaPagamento();
            forma.setNumSeq(item.getIdFormaPagamento());
            registro.setForma(forma);

            return dao.save(registro);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
