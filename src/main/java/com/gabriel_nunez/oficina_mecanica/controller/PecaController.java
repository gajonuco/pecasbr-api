/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.controller.PecaController
 *  com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.PathDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.PecaImagemDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.PecaImagemUrlDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.ReordenarImagensDTO
 *  com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  com.gabriel_nunez.oficina_mecanica.service.ByteArrayMultipartFile
 *  com.gabriel_nunez.oficina_mecanica.service.IPecaService
 *  com.gabriel_nunez.oficina_mecanica.service.IUploadService
 *  com.gabriel_nunez.oficina_mecanica.service.PecaImagemService
 *  org.springframework.data.domain.Page
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.DeleteMapping
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PatchMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.multipart.MultipartFile
 */
package com.gabriel_nunez.oficina_mecanica.controller;

import com.gabriel_nunez.oficina_mecanica.dto.FiltroRankingProdutosDTO;
import com.gabriel_nunez.oficina_mecanica.dto.PathDTO;
import com.gabriel_nunez.oficina_mecanica.dto.PecaImagemDTO;
import com.gabriel_nunez.oficina_mecanica.dto.PecaImagemUrlDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ProdutoMaisPedidoDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ReordenarImagensDTO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.service.ByteArrayMultipartFile;
import com.gabriel_nunez.oficina_mecanica.service.IPecaService;
import com.gabriel_nunez.oficina_mecanica.service.IUploadService;
import com.gabriel_nunez.oficina_mecanica.service.PecaImagemService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value={"*"})
@RestController
public class PecaController {
    private final IPecaService service;
    private final IUploadService upload;
    private final PecaImagemService pecaImagemService;

    public PecaController(IPecaService service, IUploadService upload, PecaImagemService pecaImagemService) {
        this.service = service;
        this.upload = upload;
        this.pecaImagemService = pecaImagemService;
    }

    @PostMapping(value={"/peca"})
    public ResponseEntity<Peca> novaPeca(@RequestBody Peca pecaNova) {
        try {
            Peca peca = this.service.inserirNovaPeca(pecaNova);
            return ResponseEntity.status((int)201).body(peca);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value={"/peca/{id}/imagem/url"})
    public ResponseEntity<?> adicionarImagemViaUrl(@PathVariable Integer id, @RequestBody PecaImagemUrlDTO dto) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] bytes = (byte[])restTemplate.getForObject(dto.getUrl(), byte[].class, new Object[0]);
            if (bytes == null || bytes.length == 0) {
                return ResponseEntity.badRequest().body("N\u00e3o foi poss\u00edvel baixar a imagem.");
            }
            ByteArrayMultipartFile multipartFile = new ByteArrayMultipartFile(bytes, "imagem.jpg", "image/jpeg");
            String path = this.upload.uploadFile((MultipartFile)multipartFile);
            if (path == null) {
                return ResponseEntity.badRequest().build();
            }
            //String linkFoto = "https://projetoreal.dev.br/assets/img/" + path;
              String linkFoto = "http://localhost:8080/images/" + path;
            PecaImagemDTO imagemDTO = this.pecaImagemService.adicionarImagem(id, linkFoto, dto.getPrincipal());
            return ResponseEntity.status((int)201).body(imagemDTO);
        }
        catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PutMapping(value={"/peca/{idPeca}"})
    public ResponseEntity<Peca> atualizarProduto(@RequestBody Peca pecaAtual, @PathVariable int idPeca) {
        try {
            if (idPeca != pecaAtual.getId()) {
                return ResponseEntity.badRequest().build();
            }
            Peca res = this.service.alterarPeca(pecaAtual);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value={"/peca/categoria/{id}"})
    public ResponseEntity<ArrayList<Peca>> recuperarPorCategoria(@PathVariable(name="id") int idCategoriaPeca) {
        CategoriaPeca categoria = new CategoriaPeca();
        categoria.setId(Integer.valueOf(idCategoriaPeca));
        return ResponseEntity.ok(this.service.listarPorCategoria(categoria));
    }

    @GetMapping(value={"/peca"})
    public ResponseEntity<Page<Peca>> recuperarTodos(@RequestParam(defaultValue="1") int pageNumber) {
        return ResponseEntity.ok(this.service.listarDestaques(pageNumber));
    }

    @GetMapping(value={"/peca/todos"})
    public ResponseEntity<ArrayList<Peca>> buscarTodos() {
        return ResponseEntity.ok(this.service.listarTodos());
    }

    @GetMapping(value={"/peca/{id}"})
    public ResponseEntity<Peca> recuperarPorId(@PathVariable(name="id") int idPeca) {
        Peca peca = this.service.recuperarPorId(idPeca);
        if (peca != null) {
            return ResponseEntity.ok(peca);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value={"/peca/busca"})
    public ResponseEntity<Page<Peca>> buscarPorPalavraChave(@RequestParam(name="key") String key, @RequestParam(name="pageNumber", defaultValue="0") int pagina) {
        System.out.println("key = " + key);
        if (key != null) {
            return ResponseEntity.ok(this.service.listarPorPalavraChave(key, pagina));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value={"/peca/upload"})
    public ResponseEntity<PathDTO> uploadFoto(@RequestParam(value="arquivo") MultipartFile arquivo) {
        String path = this.upload.uploadFile(arquivo);
        if (path == null) {
            return ResponseEntity.badRequest().build();
        }
        PathDTO pathDTO = new PathDTO();
        pathDTO.setPathToFile(path);
        return ResponseEntity.status((int)201).body(pathDTO);
    }

    @PostMapping(value={"/produtos_mais_pedidos"})
    public ResponseEntity<ArrayList<ProdutoMaisPedidoDTO>> obterProdutosMaisPedidos(@RequestBody FiltroRankingProdutosDTO filtro) {
        try {
            ArrayList produtos = this.service.listarProdutosMaisPedidos(filtro);
            return ResponseEntity.ok(produtos);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value={"/peca/{idPeca}/imagens"})
    public ResponseEntity<List<PecaImagemDTO>> listarImagensPorPeca(@PathVariable Integer idPeca) {
        try {
            List imagens = this.pecaImagemService.listarImagensPorPeca(idPeca);
            return ResponseEntity.ok(imagens);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value={"/peca/{id}/imagem"})
    public ResponseEntity<?> adicionarImagem(@PathVariable Integer id, @RequestParam(value="arquivo") MultipartFile arquivo, @RequestParam(value="principal", defaultValue="0") Integer principal) {
        try {
            String path = this.upload.uploadFile(arquivo);
            if (path == null) {
                return ResponseEntity.badRequest().build();
            }
            // String linkFoto = "https://projetoreal.dev.br/assets/img/" + path;
               String linkFoto = "http://localhost:8080/images/" + path;
            PecaImagemDTO dto = this.pecaImagemService.adicionarImagem(id, linkFoto, principal);
            return ResponseEntity.status((int)201).body(dto);
        }
        catch (RuntimeException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @DeleteMapping(value={"peca/imagem/{idImagem}"})
    public ResponseEntity<?> removerImagem(@PathVariable Integer idImagem) {
        try {
            this.pecaImagemService.removerImagem(idImagem);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value={"/peca/{idPeca}/imagem/{idImagem}/principal"})
    public ResponseEntity<?> definirImagemPrincipal(@PathVariable Integer idPeca, @PathVariable Integer idImagem) {
        try {
            this.pecaImagemService.definirPrincipal(idPeca, idImagem);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(value={"/peca/{id}/imagens/reordenar"})
    public ResponseEntity<Void> reordenarImagens(@PathVariable Integer id, @RequestBody List<ReordenarImagensDTO> ordens) {
        this.pecaImagemService.reordenarImagens(ordens);
        return ResponseEntity.ok().build();
    }
}

