package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.FormaPagamento;
import com.gabriel_nunez.oficina_mecanica.service.IFormaPgtoService;


@RestController
@CrossOrigin("*")
public class FormaPagamentoController {

	@Autowired
	private IFormaPgtoService service;

	@GetMapping("/formaspagamento")
	public ResponseEntity<ArrayList<FormaPagamento>> recuperarTodas(@RequestParam(name = "visivel") String visivel) {
		if (visivel.equals("1")) {
			return ResponseEntity.ok(service.buscarVisiveis());
		} else {
			return ResponseEntity.ok(service.buscarTodas());
		}
	}

	@GetMapping("/formaspagamento/{id}")
	public ResponseEntity<FormaPagamento> recuperarPeloId(@PathVariable int id) {
		FormaPagamento forma = service.buscarPeloId(id);
		if (forma != null) {
			return ResponseEntity.ok(forma);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/formaspagamento")
	public ResponseEntity<FormaPagamento> inserirNovo(@RequestBody FormaPagamento novo) {
		try {
			if (service.atualizar(novo) != null) {
				return ResponseEntity.ok(novo);
			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

	}
	
	@PutMapping("/formaspagamento")
	public ResponseEntity<FormaPagamento> atualizar(@RequestBody FormaPagamento novo) {
		try {
			if (service.atualizar(novo) != null) {
				return ResponseEntity.ok(novo);
			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

	}

}