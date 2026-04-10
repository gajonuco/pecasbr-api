/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.RegistroFinanceiroDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO
 *  com.gabriel_nunez.oficina_mecanica.model.FormaPagamento
 *  com.gabriel_nunez.oficina_mecanica.model.Pedido
 *  com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro
 *  com.gabriel_nunez.oficina_mecanica.service.FluxoFinanceiroImpl
 *  com.gabriel_nunez.oficina_mecanica.service.IFluxoFinanceiroService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.RegistroFinanceiroDAO;
import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import com.gabriel_nunez.oficina_mecanica.model.Pedido;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;
import com.gabriel_nunez.oficina_mecanica.service.IFluxoFinanceiroService;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FluxoFinanceiroImpl
implements IFluxoFinanceiroService {
    @Autowired
    private RegistroFinanceiroDAO dao;

    public int gerarFluxoFinanceiro(RegistroFinanceiroDTO registro) {
        LocalDate vencimento = LocalDate.now();
        LocalDate dataParcela = LocalDate.of(vencimento.getYear(), vencimento.getMonth(), registro.getDiaVencimento());
        for (int parcela = 1; parcela <= registro.getTotalParcelas(); ++parcela) {
            RegistroFinanceiro r = new RegistroFinanceiro();
            r.setForma(registro.getFormaPagamento());
            r.setNumParcela(parcela);
            r.setTotalParcelas(registro.getTotalParcelas());
            r.setPercentRetencao(registro.getFormaPagamento().getRetencao());
            r.setValorBruto(registro.getPedido().getValorTotal() / (double)registro.getTotalParcelas());
            r.setValorRetencao(r.getValorBruto() * r.getPercentRetencao() / 100.0);
            r.setVencimento(dataParcela);
            dataParcela = dataParcela.plusMonths(1L);
            r.setValorLiquido(registro.getPedido().getValorTotal() * (1.0 - registro.getFormaPagamento().getRetencao() / 100.0) / (double)registro.getTotalParcelas());
            r.setStatus(0);
            r.setPedido(registro.getPedido());
            this.dao.save(r);
        }
        return registro.getTotalParcelas();
    }

    public ArrayList<ItemFinanceiroDTO> recuperarRegistros() {
        return this.dao.recuperarItensFinanceiros();
    }

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
            pedido.setId(Integer.valueOf(item.getIdPedido()));
            registro.setPedido(pedido);
            FormaPagamento forma = new FormaPagamento();
            forma.setNumSeq(item.getIdFormaPagamento());
            registro.setForma(forma);
            return (RegistroFinanceiro)this.dao.save(registro);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

