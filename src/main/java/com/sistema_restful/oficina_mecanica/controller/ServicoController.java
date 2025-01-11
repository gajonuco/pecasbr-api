package com.sistema_restful.oficina_mecanica.controller;

import com.sistema_restful.oficina_mecanica.model.Servico;
import com.sistema_restful.oficina_mecanica.service.ServicoService;
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
@RequestMapping("/api/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    // Adicionar um serviço
    @PostMapping
    public ResponseEntity<Object> salvarServico(@Valid @RequestBody Servico servico) {
        try {
            Servico novoServico = servicoService.salvarServico(servico);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoServico);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "Dados inválidos",
                    "mensagem", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "erro", "Erro interno",
                    "mensagem", "Ocorreu um erro ao processar sua solicitação."
            ));
        }
    }

    // Listar todos os serviços com paginação
    @GetMapping
    public ResponseEntity<Page<Servico>> listarServicos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Page<Servico> servicos = servicoService.listarServicos(pageable);
        return ResponseEntity.ok(servicos);
    }

    // Listar serviços específicos por uma lista de IDs
    @PostMapping("/especificos")
    public ResponseEntity<Object> listarServicosEspecificos(@RequestBody List<Long> ids) {
        try {
            List<Servico> servicos = servicoService.listarServicosEspecificos(ids);
            return ResponseEntity.ok(servicos);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "erro", "IDs inválidos",
                    "mensagem", ex.getMessage()
            ));
        }
    }


    // Atualizar um serviço por ID
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        return new ResponseEntity<>(servicoService.atualizarServico(id, servico), HttpStatus.OK);
    }

    // Excluir um serviço por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarServico(@PathVariable Long id) {
        servicoService.deletarServico(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
