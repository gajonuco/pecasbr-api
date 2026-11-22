package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.AcaoSugerida;
import com.gajonuco.pecasbr.dto.PecaConsultaDTO;
import com.gajonuco.pecasbr.dto.PecaVariacaoConsultaDTO;
import com.gajonuco.pecasbr.dto.PecaVariacaoDTO;
import com.gajonuco.pecasbr.model.CategoriaPeca;
import com.gajonuco.pecasbr.model.Peca;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CatalogoToolsTest {

    private final IPecaService pecaService = mock(IPecaService.class);
    private final ICategoriaPecaService categoriaPecaService = mock(ICategoriaPecaService.class);
    private final PecaVariacaoService pecaVariacaoService = mock(PecaVariacaoService.class);
    private final AcaoSugeridaHolder acaoSugeridaHolder = new AcaoSugeridaHolder();
    private final CatalogoTools catalogoTools =
            new CatalogoTools(pecaService, categoriaPecaService, pecaVariacaoService, acaoSugeridaHolder);

    @Test
    void deveRecusarQuandoProdutoNaoEncontrado() {
        when(pecaService.recuperarPorId(999)).thenReturn(null);

        String resultado = catalogoTools.sugerirAdicaoAoCarrinho(999, 1, null, null);

        assertThat(resultado).contains("não encontrado");
        assertThat(acaoSugeridaHolder.consumir()).isNull();
    }

    @Test
    void devePedirCorQuandoProdutoExigeEElaNaoForInformada() {
        Peca peca = new Peca();
        peca.setCorUnica(false);
        peca.setTamanhoUnico(true);
        when(pecaService.recuperarPorId(1)).thenReturn(peca);

        String resultado = catalogoTools.sugerirAdicaoAoCarrinho(1, 1, null, null);

        assertThat(resultado).contains("cor");
        assertThat(acaoSugeridaHolder.consumir()).isNull();
    }

    @Test
    void deveRecusarCombinacaoDeVariacaoInexistente() {
        Peca peca = new Peca();
        peca.setCorUnica(false);
        peca.setTamanhoUnico(false);
        when(pecaService.recuperarPorId(1)).thenReturn(peca);
        when(pecaVariacaoService.buscarPorCorETamanho(1, "Roxo", "GG"))
                .thenThrow(new RuntimeException("Variação não encontrada."));

        String resultado = catalogoTools.sugerirAdicaoAoCarrinho(1, 1, "Roxo", "GG");

        assertThat(resultado).contains("não existe");
        assertThat(acaoSugeridaHolder.consumir()).isNull();
    }

    @Test
    void deveRecusarQuandoEstoqueDaVariacaoForInsuficiente() {
        Peca peca = new Peca();
        peca.setCorUnica(false);
        peca.setTamanhoUnico(false);
        when(pecaService.recuperarPorId(1)).thenReturn(peca);

        PecaVariacaoDTO variacao = new PecaVariacaoDTO();
        variacao.setQuantidadeEstoque(0);
        when(pecaVariacaoService.buscarPorCorETamanho(1, "Preto", "M")).thenReturn(variacao);

        String resultado = catalogoTools.sugerirAdicaoAoCarrinho(1, 1, "Preto", "M");

        assertThat(resultado).contains("estoque");
        assertThat(acaoSugeridaHolder.consumir()).isNull();
    }

    @Test
    void devePrepararAcaoQuandoTudoEstaValido() {
        Peca peca = new Peca();
        peca.setCorUnica(false);
        peca.setTamanhoUnico(false);
        when(pecaService.recuperarPorId(1)).thenReturn(peca);

        PecaVariacaoDTO variacao = new PecaVariacaoDTO();
        variacao.setQuantidadeEstoque(5);
        when(pecaVariacaoService.buscarPorCorETamanho(1, "Preto", "M")).thenReturn(variacao);

        String resultado = catalogoTools.sugerirAdicaoAoCarrinho(1, 1, "Preto", "M");

        assertThat(resultado).contains("validado");
        AcaoSugerida acao = acaoSugeridaHolder.consumir();
        assertThat(acao).isNotNull();
        assertThat(acao.pecaId()).isEqualTo(1);
        assertThat(acao.cor()).isEqualTo("Preto");
    }

    @Test
    void deveRetornarVariacoesDisponiveisDoProduto() {
        PecaVariacaoDTO variacaoComEstoque = new PecaVariacaoDTO();
        variacaoComEstoque.setCor("Preto");
        variacaoComEstoque.setTamanho("M");
        variacaoComEstoque.setQuantidadeEstoque(3);

        PecaVariacaoDTO variacaoSemEstoque = new PecaVariacaoDTO();
        variacaoSemEstoque.setCor("Branco");
        variacaoSemEstoque.setTamanho("G");
        variacaoSemEstoque.setQuantidadeEstoque(0);

        when(pecaVariacaoService.listar(1)).thenReturn(List.of(variacaoComEstoque, variacaoSemEstoque));

        List<PecaVariacaoConsultaDTO> resultado = catalogoTools.consultarVariacoesDoProduto(1);

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).disponivel()).isTrue();
        assertThat(resultado.get(1).disponivel()).isFalse();
    }

    @Test
    void deveRetornarListaVaziaQuandoProdutoNaoTemVariacoes() {
        when(pecaVariacaoService.listar(99)).thenReturn(List.of());
        assertThat(catalogoTools.consultarVariacoesDoProduto(99)).isEmpty();
    }

    @Test
    void deveRetornarProdutosQuandoEncontradosPorNome() {
        when(pecaService.listarPorPalavraChave(eq("camisa"), anyInt()))
                .thenReturn(new PageImpl<>(List.of(umaPeca())));

        List<PecaConsultaDTO> resultado = catalogoTools.consultarProdutosPorNome("camisa");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).nome()).isEqualTo("Camisa Teste");
        assertThat(resultado.get(0).emPromocao()).isTrue();
        assertThat(resultado.get(0).prontaEntrega()).isTrue();
    }

    @Test
    void deveRetornarListaVaziaQuandoCategoriaNaoEncontrada() {
        when(categoriaPecaService.recuperarPorPalavraChave("inexistente"))
                .thenReturn(new ArrayList<>());

        List<PecaConsultaDTO> resultado = catalogoTools.consultarProdutosPorCategoria("inexistente");

        assertThat(resultado).isEmpty();
        verifyNoInteractions(pecaService);
    }

    @Test
    void deveRetornarNullQuandoProdutoNaoEncontradoPorId() {
        when(pecaService.recuperarPorId(999)).thenReturn(null);

        assertThat(catalogoTools.consultarProdutoPorId(999)).isNull();
    }

    private Peca umaPeca() {
        Peca peca = new Peca();
        peca.setId(1);
        peca.setNome("Camisa Teste");
        peca.setDetalhe("Camisa de algodão");
        peca.setPreco(BigDecimal.valueOf(99.9));
        peca.setPrecoPromo(BigDecimal.valueOf(79.9));
        peca.setDisponivel(1);
        peca.setProntaEntrega(1);
        peca.setDestaque(0);
        peca.setQuantidadeEstoque(10);
        peca.setEstoqueMinimo(2);
        peca.setEstoqueCritico(1);
        CategoriaPeca categoria = new CategoriaPeca();
        categoria.setNome("Camisas");
        peca.setCategoriaPeca(categoria);
        return peca;
    }
}