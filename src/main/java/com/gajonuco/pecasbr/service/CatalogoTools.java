package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.*;
import com.gajonuco.pecasbr.model.CategoriaPeca;
import com.gajonuco.pecasbr.model.Peca;
import dev.langchain4j.agent.tool.P;
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

    private final IPecaService pecaService;
    private final ICategoriaPecaService categoriaPecaService;
    private final PecaVariacaoService pecaVariacaoService;
    private final AcaoSugeridaHolder acaoSugeridaHolder;


    public CatalogoTools(IPecaService pecaService,
                         ICategoriaPecaService categoriaPecaService,
                         PecaVariacaoService pecaVariacaoService,
                         AcaoSugeridaHolder acaoSugeridaHolder) {
        this.pecaService = pecaService;
        this.categoriaPecaService = categoriaPecaService;
        this.pecaVariacaoService = pecaVariacaoService;
        this.acaoSugeridaHolder = acaoSugeridaHolder;
    }

    @Tool("Busca produtos de catálogo real pelo nome ou palavra-chave da descrição")
    public List<PecaConsultaDTO> consultarProdutosPorNome(String termoBusca) {
        Page<Peca> pagina = pecaService.listarPorPalavraChave(termoBusca, PAGINA_PADRAO);
        return mapear(pagina.getContent());
    }

    @Tool("Busca o detalhe de um produto específico do catálogo pelo seu id")
    public PecaConsultaDTO consultarProdutoPorId(int id) {
        try {
            Peca peca = pecaService.recuperarPorId(id);
            return peca != null ? PecaConsultaDTO.from(peca) : null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Tool("Lista produtos do catálogo real que pertencem a uma categoria, pelo nome da categoria ")
    public List<PecaConsultaDTO> consultarProdutosPorCategoria(String nomeCategoria) {
        ArrayList<CategoriaPeca> categorias = categoriaPecaService.recuperarPorPalavraChave(nomeCategoria);
        if (categorias.isEmpty()) {
            return List.of();
        }
        ArrayList<Peca> pecas = pecaService.listarPorCategoria(categorias.get(0));
        return mapear(pecas);
    }

    @Tool("Lista as variações de cor e tamanho disponíveis para um produto específico, pelo seu id." +
            "Use antes de adicionar um produto ao carrinho, se ele tiver variações.")
    public List<PecaVariacaoConsultaDTO> consultarVariacoesDoProduto(int pecaId) {
        try {
            return pecaVariacaoService.listar(pecaId).stream()
                    .map(PecaVariacaoConsultaDTO::from)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            return List.of();
        }
    }


    @Tool("Valida um produto, quantidade e variação (cor/tamanho) e prepara a sugestão de adicionar " +
            "ao carrinho. Use consultarVariacoesDoProduto antes, se o produto tiver variações, para saber "+
                    "as opções e o estoque válidos. Informe cor e tamanho apenas quando o produto exigir.")

    public String sugerirAdicaoAoCarrinho(
            int pecaId,
            int quantidade,
            @P(value = "cor desejada, apenas se o produto tiver variação de cor", required = false) String cor,
            @P(value = "tamanho desejado, apenas se o produto tiver variação de tamanho", required = false) String tamanho) {

        if (quantidade <= 0) {
            return "Quantidade inválida.";

        }

        Peca peca;
        try {
            peca = pecaService.recuperarPorId(pecaId);

        } catch (RuntimeException e) {
            peca = null;

        }
        if (peca == null) {
            return "Produto não encontrado.";

        }

        boolean precisaCor = !Boolean.TRUE.equals(peca.getCorUnica());
        boolean precisaTamanho = !Boolean.TRUE.equals(peca.getTamanhoUnico());

        if (precisaCor && (cor == null || cor.isBlank())) {
            return "É necessário informar a cor desejada para este produto.";

        }
        if (precisaTamanho && (tamanho == null || tamanho.isBlank())) {
            return "É necessário informar o tamanho desejado para este produto.";

        }

        if (precisaCor || precisaTamanho) {
            PecaVariacaoDTO variacao;
            try {
                variacao = pecaVariacaoService.buscarPorCorETamanho(pecaId, cor, tamanho);
            } catch (RuntimeException e) {
                return "Essa combinação de cor e tamanho não existe para este produto.";
            }
            Integer estoqueVariacao = variacao.getQuantidadeEstoque();
            if (estoqueVariacao == null || estoqueVariacao < quantidade) {
                return "Não há estoque suficiente dessa combinação de cor e tamanho.";
            }
        } else if (!peca.podeVender(quantidade)) {
            return "Não há estoque suficiente deste produto.";
        }

        AcaoSugerida acao = new AcaoSugerida(TipoAcaoAgente.ADICIONAR_CARRINHO, pecaId, quantidade, cor, tamanho);
        acaoSugeridaHolder.definir(acao);

        return "Produto validado. A ação de adicionar ao carrinho foi preparada. Você só pode dizer ao " +
                "cliente que o produto foi adicionado quando receber a confirmação do sistema.";
    }


    private List<PecaConsultaDTO> mapear(List<Peca> pecas) {
        return pecas.stream()
                .limit(LIMITE_RESULTADOS)
                .map(PecaConsultaDTO::from)
                .collect(Collectors.toList());
    }
}
