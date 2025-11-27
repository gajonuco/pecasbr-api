package com.gabriel_nunez.oficina_mecanica.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gabriel_nunez.oficina_mecanica.service.IUploadService;
import com.gabriel_nunez.oficina_mecanica.dto.PathDTO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.service.IPecaService;

@CrossOrigin("*")
@RestController
public class PecaController {

    @Autowired
    private IPecaService service;

    @Autowired
    private IUploadService upload;

    @PostMapping("/peca")
    public ResponseEntity<Peca> novaPeca(@RequestBody Peca pecaNova){
        try {
            service.inserirNovaPeca(pecaNova);
            return ResponseEntity.status(201).body(pecaNova);
            
        } catch (Exception ex) {
            // TODO: handle exception
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/peca/{idPeca}")
    public ResponseEntity<Peca> atualizarProduto(@RequestBody Peca pecaAtual, @PathVariable int idPeca){

    try {
        if(idPeca != pecaAtual.getId()){
            return ResponseEntity.badRequest().build();
        }
        Peca res = this.service.alterarPeca(pecaAtual);
        return ResponseEntity.ok(res);
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
    return ResponseEntity.badRequest().build();
        
    }


    @GetMapping("/peca/categoria/{id}")
    public ResponseEntity<ArrayList<Peca>> recuperarPorCategoria(@PathVariable(name = "id") int idCategoriaPeca ){
        CategoriaPeca categoria = new CategoriaPeca();
        categoria.setId(idCategoriaPeca);
        return ResponseEntity.ok(service.listarPorCategoria(categoria));
    }

	@GetMapping("/peca")
	public ResponseEntity<Page<Peca>> recuperarTodos(@RequestParam(defaultValue = "1") int pageNumber){
		return ResponseEntity.ok(service.listarDestaques(pageNumber));
		
	}

    @GetMapping("/peca/todos")
    public ResponseEntity<ArrayList<Peca>> buscarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/peca/{id}")
    public ResponseEntity <Peca> recuperarPorId(@PathVariable(name = "id") int idPeca) {
        Peca peca = service.recuperarPorId(idPeca);
        if(peca != null){
            return ResponseEntity.ok(peca);

        }
        return ResponseEntity.notFound().build();

    }
    
	@GetMapping("/peca/busca")
	public ResponseEntity<Page<Peca>> buscarPorPalavraChave(@RequestParam (name="key") String key, @RequestParam(name="pageNumber", defaultValue = "0") int pagina){
		System.out.println("key = " + key);
		if (key != null) {
			return ResponseEntity.ok(service.listarPorPalavraChave(key, pagina));
		}
		return ResponseEntity.badRequest().build();
	
    
    }

    @PostMapping("/peca/upload")
    public ResponseEntity<PathDTO> uploadFoto(@RequestParam("arquivo") MultipartFile arquivo){
        String path = upload.uploadFile(arquivo);
        if(path == null){
            return ResponseEntity.badRequest().build();
        }
        PathDTO pathDTO = new PathDTO();
        pathDTO.setPathToFile(path);
        return ResponseEntity.status(201).body(pathDTO);
    }
}