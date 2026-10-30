package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.PecaConsultaDTO;
import com.gajonuco.pecasbr.model.CategoriaPeca;
import com.gajonuco.pecasbr.model.Peca;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatalogoTools {

    private static final int PAGINA_PADRAO = 0;
    private static final int LIMITE_RESULTADOS = 10;

    private final  IPecaService pecaService;
    private final ICategoriaPecaService categoriaPecaService;

    public CatalogoTools(IPecaService pecaService, ICategoriaPecaService categoriaPecaService){
        this.pecaService = pecaService;
        this.categoriaPecaService = categoriaPecaService;
    }
    @Tool("Busca produtos de catálogo real pelo nome ou palavra-chave da descrição")
    public List<PecaConsultaDTO> consultarProdutosPorNome(String termoBusca) {
        Page<Peca> pagina = pecaService.listarPorPalavraChave(termoBusca, PAGINA_PADRAO);
        return mapear(pagina.getContent());
    }

    @Tool("Busca o detalhe de um produto específico do catálogo pelo seu id")
    public PecaConsultaDTO consultarProdutoPorId(int id){
        try{
            Peca peca = pecaService.recuperarPorId(id);
            return peca != null ? PecaConsultaDTO.from(peca) : null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Tool("Lista produtos do catálogo real que pertencem a uma categoria, pelo nome da categoria ")
    public List<PecaConsultaDTO> consultarProdutosPorCategoria(String nomeCategoria) {
        ArrayList<CategoriaPeca> categorias = categoriaPecaService.recuperarPorPalavraChave(nomeCategoria);
        if(categorias.isEmpty()){
            return List.of();
        }
        ArrayList<Peca> pecas = pecaService.listarPorCategoria(categorias.get(0));
        return mapear(pecas);
    }


    private List<PecaConsultaDTO> mapear(List<Peca> pecas) {
        return pecas.stream()
                .limit(LIMITE_RESULTADOS)
                .map(PecaConsultaDTO::from)
                .collect(Collectors.toList());
    }
}
