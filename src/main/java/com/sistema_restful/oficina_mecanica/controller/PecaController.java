package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @PostMapping
    public ResponseEntity<Object> salvarPeca(@Valid @RequestBody Peca peca) {
        try {
            Peca novaPeca = pecaService.salvarPeca(peca);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPeca);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Requisição inválida",
                    "mensagem", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "erro", "Erro interno no servidor",
                    "mensagem", "Ocorreu um erro inesperado ao salvar a peça. Tente novamente mais tarde."
            ));
        }
    }


    // Listar todas as peças com paginação e ordenação
    @GetMapping
    public ResponseEntity<Page<Peca>> listarPecas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Peca> pecas = pecaService.listarPecas(pageable);
        return ResponseEntity.ok(pecas);
    }

    // Listar peças específicas por lista de IDs
    @PostMapping("/especificas")
    public ResponseEntity<Object> listarPecasEspecificas(@RequestBody List<Long> ids) {
        try {
            List<Peca> pecas = pecaService.listarPecasEspecificas(ids);
            return ResponseEntity.ok(pecas);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Requisição inválida",
                    "mensagem", ex.getMessage()
            ));
        }
    }



    // Atualizar uma peça pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizarPeca(@PathVariable Long id, @RequestBody Peca peca) {
        return new ResponseEntity<>(pecaService.atualizarPeca(id, peca), HttpStatus.OK);
    }

    // Excluir uma peça pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        pecaService.deletarPeca(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Excluir múltiplas peças
    @DeleteMapping("/batch")
    public ResponseEntity<Void> deletarPecas(@RequestBody List<Long> ids) {
        pecaService.deletarPecas(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
