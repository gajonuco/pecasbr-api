package com.sistema_restful.oficina_mecanica.controllers;

import com.sistema_restful.oficina_mecanica.dto.OrcamentoDTO;
import com.sistema_restful.oficina_mecanica.dto.OrcamentoResponseDTO;
import com.sistema_restful.oficina_mecanica.model.Orcamento;
import com.sistema_restful.oficina_mecanica.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @PostMapping
    public ResponseEntity<Object> salvarOrcamento(@RequestBody OrcamentoDTO orcamentoDTO) {
        try {
            OrcamentoResponseDTO orcamento = orcamentoService.salvarOrcamento(orcamentoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(orcamento);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Dados inválidos",
                    "mensagem", ex.getMessage()
            ));
        }
    }

    // Listar todos os orçamentos
    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDTO>> listarOrcamentos() {
        List<OrcamentoResponseDTO> orcamentos = orcamentoService.listarOrcamentos();
        return ResponseEntity.ok(orcamentos);
    }

    // Listar orçamentos específicos por uma lista de IDs
    @PostMapping("/especificos")
    public ResponseEntity<List<OrcamentoResponseDTO>> listarOrcamentosEspecificos(@RequestBody List<Long> ids) {
        List<OrcamentoResponseDTO> orcamentos = orcamentoService.listarOrcamentosEspecificos(ids);
        return ResponseEntity.ok(orcamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> atualizarOrcamento(@PathVariable Long id, @RequestBody OrcamentoDTO orcamentoDTO) {
        Orcamento orcamentoAtualizado = orcamentoService.atualizarOrcamento(id, orcamentoDTO);
        return ResponseEntity.ok(orcamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrcamento(@PathVariable Long id) {
        orcamentoService.deletarOrcamento(id);
        return ResponseEntity.noContent().build();
    }

}
