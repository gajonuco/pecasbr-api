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
import org.springframework.web.bind.annotation.RestController;

import com.gabriel_nunez.oficina_mecanica.model.Frete;
import com.gabriel_nunez.oficina_mecanica.service.IFreteService;

@RestController
@CrossOrigin("*")
public class FreteController {

	@Autowired
	private IFreteService service;

	@GetMapping("/fretesdisponiveis")
	public ResponseEntity<ArrayList<Frete>> buscarDisponiveis() {
		return ResponseEntity.ok(service.recuperarDisponiveis());
	}
	
	@GetMapping("/fretes/{id}")
	public ResponseEntity<Frete> recuperarPeloId(@PathVariable int id){
		Frete res = service.recuperarPeloId(id);
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/fretes/prefixo/{prefixo}")
	public ResponseEntity<Frete> recuperarPeloPrefixo(@PathVariable String prefixo){
		Frete res = service.recuperarPeloPrefixo(prefixo);
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.notFound().build();
	}

 	@GetMapping("/fretes")
	public ResponseEntity<ArrayList<Frete>> buscarTodos() {
		return ResponseEntity.ok(service.recuperarTodos());
	}

	@PostMapping("/fretes")
	public ResponseEntity<Frete> adicionarNovo(@RequestBody Frete novo) {
		try {
			Frete adicionado = service.inserirFrete(novo);
			if (adicionado != null) {
				return ResponseEntity.status(201).body(adicionado);
			}
		} catch (Exception ex) {
			System.out.println("DEBUG - Erro ao gravar FRETE " + ex.getMessage());
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/fretes")
	public ResponseEntity<Frete> atualizarFrete(@RequestBody Frete frete) {
		try {
			Frete atualizado = service.atualizarFrete(frete);
			if (atualizado != null) {
				return ResponseEntity.ok(atualizado);
			}
		} catch (Exception ex) {
			System.out.println("DEBUG - Erro ao atualizar FRETE " + ex.getMessage());
		}
		return ResponseEntity.badRequest().build();
	}

}
