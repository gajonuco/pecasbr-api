package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.dto.ItemFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.dto.RegistroFinanceiroDTO;
import com.gabriel_nunez.oficina_mecanica.model.RegistroFinanceiro;
import com.gabriel_nunez.oficina_mecanica.service.IFluxoFinanceiroService;

@RestController
public class FinanceiroController {

    @Autowired
    IFluxoFinanceiroService fluxo;

    @PostMapping("/financeiro")
    public String gerarFluxoFinanceiro(@RequestBody RegistroFinanceiroDTO registro) {
        fluxo.gerarFluxoFinanceiro(registro);
        return "ok";
    }

    @GetMapping("/financeiro")
    public ResponseEntity<ArrayList<ItemFinanceiroDTO>> recuperarTodos() {
        return ResponseEntity.ok(fluxo.recuperarRegistros());
    }

    @PutMapping("/financeiro")
    public ResponseEntity<RegistroFinanceiro> atualizarStatus(@RequestBody ItemFinanceiroDTO item) {
        try {
            RegistroFinanceiro registro = fluxo.alterarStatus(item);
            if (registro != null) {
                return ResponseEntity.ok(registro);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
