package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dto.PecaConsultaDTO;
import com.gajonuco.pecasbr.model.CategoriaPeca;
import com.gajonuco.pecasbr.model.Peca;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CatalogoToolsTest {

    private final IPecaService pecaService = mock(IPecaService.class);
    private final ICategoriaPecaService categoriaPecaService = mock(ICategoriaPecaService.class);
    private final CatalogoTools catalogoTools = new CatalogoTools(pecaService, categoriaPecaService);

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
        peca.setPreco(99.9);
        peca.setPrecoPromo(79.9);
        peca.setDisponivel(1);
        peca.setProntaEntrega(1);
        peca.setDestaque(0);
        CategoriaPeca categoria = new CategoriaPeca();
        categoria.setNome("Camisas");
        peca.setCategoriaPeca(categoria);
        return peca;
    }
}