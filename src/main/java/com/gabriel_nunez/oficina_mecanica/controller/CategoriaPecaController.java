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

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.service.ICategoriaPecaService;

@CrossOrigin("*")
@RestController
public class CategoriaPecaController{
    
    @Autowired
    ICategoriaPecaService service;

    @GetMapping("/categoria_peca")
    public ResponseEntity<ArrayList<CategoriaPeca>> listarTodas(){
        return ResponseEntity.ok().body(service.recuperarTodasCategoriasPecas());

    }
    @GetMapping("/categoria_by_id")
    public ResponseEntity<ArrayList<CategoriaPeca>> recuperarTodasOrdenadasPeloId(){
        return ResponseEntity.ok(service.recuperarTodasPeloId());
    }

    @GetMapping("categoria_peca/search")
    public ResponseEntity<ArrayList<CategoriaPeca>> recuperarPorPalavraChave(@RequestParam(name = "key") String palavraChave){
        if(palavraChave != null){
            return ResponseEntity.ok().body(service.recuperarPorPalavraChave(palavraChave));
        } else{
            return ResponseEntity.badRequest().build();

        }
      
    }

    @GetMapping("categoria_peca/{id}")
    public ResponseEntity<CategoriaPeca> recuperarDetalhes(@PathVariable(name ="id") int id){
        CategoriaPeca resultado = service.recuperaPorID(id);
        if(resultado != null){
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/categoria_peca")
    public ResponseEntity<CategoriaPeca> adicionarNovaCategoriaPeca(@RequestBody CategoriaPeca categoriaPeca){
        if(categoriaPeca.getId() != null){
            categoriaPeca.setId(null);
        }
        CategoriaPeca resultado = service.adicionarNovaCategoriaPeca(categoriaPeca);
        if ((resultado != null)) {
           return ResponseEntity.status(201).body(resultado);
        }

        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/categoria_peca")
    public ResponseEntity<CategoriaPeca> alterarCategoriaPeca(@RequestBody CategoriaPeca categoriaPeca){
        CategoriaPeca resultado = service.alterarCategoriaPeca(categoriaPeca);
        if(resultado != null){
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.badRequest().build();

    }
}