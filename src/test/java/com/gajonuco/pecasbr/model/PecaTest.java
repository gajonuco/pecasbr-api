package com.gajonuco.pecasbr.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários das regras de negócio do domínio Peca.
 *
 * Nenhum mock é usado aqui: Peca é um POJO/entidade JPA e todas as regras
 * testadas (status de estoque, se pode vender, sincronização de variações)
 * são cálculos internos, sem dependência de banco, HTTP ou outros serviços.
 * Por isso este é o teste mais simples (e mais rápido) da suíte.
 */
public class PecaTest {

    private Peca peca;

    @BeforeEach
    void setUp() {
        peca = criarPeca(50);
    }

    // ---------- Helpers de fixture ----------

    private Peca criarPeca(int quantidadeEstoque) {
        Peca p = new Peca();
        p.setNome("Vestido Teste");
        p.setPreco(100.0);
        p.setEstoqueMinimo(10);
        p.setEstoqueCritico(3);
        p.setQuantidadeEstoque(quantidadeEstoque);
        return p;
    }

    private PecaVariacao criarVariacao(Integer quantidadeEstoque) {
        PecaVariacao v = new PecaVariacao();
        v.setQuantidadeEstoque(quantidadeEstoque);
        return v;
    }

    private PecaImagem criarImagem(String link, Integer principal) {
        PecaImagem img = new PecaImagem();
        img.setLinkImagem(link);
        img.setPrincipal(principal);
        return img;
    }

    // ---------- getStatusEstoque(): matriz de fronteiras ----------

    @ParameterizedTest(name = "quantidade={0} -> status esperado={1}")
    @CsvSource({
            "20, NORMAL",
            "11, NORMAL",
            "10, BAIXO",   // fronteira: igual ao estoqueMinimo já conta como BAIXO
            "4,  BAIXO",
            "3,  CRITICO", // fronteira: igual ao estoqueCritico já conta como CRITICO
            "1,  CRITICO",
            "0,  ESGOTADO",
            "-5, ESGOTADO" // defensivo: o código não valida estoque negativo, mas trata como esgotado
    })
    void deveCalcularStatusDeEstoqueCorretamenteNasFronteiras(int quantidade, String statusEsperado) {
        Peca p = criarPeca(quantidade);
        assertEquals(statusEsperado, p.getStatusEstoque());
    }

    // ---------- isEstoqueBaixo / isEstoqueCritico / isEstoqueZerado ----------

    @Test
    void deveConsiderarEstoqueBaixoQuandoIgualAoMinimo() {
        Peca p = criarPeca(10); // == estoqueMinimo
        assertTrue(p.isEstoqueBaixo());
        assertFalse(p.isEstoqueCritico());
        assertFalse(p.isEstoqueZerado());
    }

    @Test
    void deveConsiderarEstoqueCriticoQuandoIgualAoCritico() {
        Peca p = criarPeca(3); // == estoqueCritico
        assertFalse(p.isEstoqueBaixo());
        assertTrue(p.isEstoqueCritico());
        assertFalse(p.isEstoqueZerado());
    }

    @Test
    void deveConsiderarEstoqueZeradoQuandoQuantidadeForZero() {
        Peca p = criarPeca(0);
        assertTrue(p.isEstoqueZerado());
        assertFalse(p.isEstoqueCritico());
        assertFalse(p.isEstoqueBaixo());
    }

    // ---------- podeVender(int) ----------

    @Test
    void devePermitirVendaQuandoQuantidadePedidaForMenorQueEstoque() {
        Peca p = criarPeca(5);
        assertTrue(p.podeVender(3));
    }

    @Test
    void devePermitirVendaQuandoQuantidadePedidaForIgualAoEstoque() {
        // fronteira: usa "estoque >= quantidade", então vender o último item é permitido
        Peca p = criarPeca(5);
        assertTrue(p.podeVender(5));
    }

    @Test
    void deveNegarVendaQuandoQuantidadePedidaForMaiorQueEstoque() {
        Peca p = criarPeca(5);
        assertFalse(p.podeVender(6));
    }

    // ---------- getEstoqueTotalVariacoes() ----------

    @Test
    void deveRetornarQuantidadeEstoqueDoProdutoQuandoNaoHaVariacoes() {
        Peca p = criarPeca(42);
        assertEquals(42, p.getEstoqueTotalVariacoes());
    }

    @Test
    void deveSomarEstoqueDeTodasAsVariacoesQuandoExistirem() {
        Peca p = criarPeca(0);
        p.setVariacoes(List.of(criarVariacao(4), criarVariacao(6)));

        assertEquals(10, p.getEstoqueTotalVariacoes());
    }

    @Test
    void deveTratarVariacaoComEstoqueNuloComoZeroAoSomar() {
        Peca p = criarPeca(0);
        p.setVariacoes(List.of(criarVariacao(4), criarVariacao(null)));

        assertEquals(4, p.getEstoqueTotalVariacoes());
    }

    // ---------- setQuantidadeEstoque(): regra de guarda com variações ----------

    @Test
    void naoDeveAlterarEstoqueDiretamenteQuandoPecaJaTemVariacoes() {
        // Regra encontrada na implementação: quando existem variações, o estoque
        // do produto pai só pode vir da soma das variações (sincronizarEstoqueTotal),
        // nunca de um set direto. Este teste documenta e protege essa regra.
        Peca p = criarPeca(20);
        p.setVariacoes(new ArrayList<>(List.of(criarVariacao(5))));

        p.setQuantidadeEstoque(999);

        assertNotEquals(999, p.getQuantidadeEstoque());
        assertEquals(20, p.getQuantidadeEstoque()); // valor anterior é mantido
    }

    @Test
    void devePermitirAlterarEstoqueDiretamenteQuandoNaoHaVariacoes() {
        Peca p = criarPeca(20);

        p.setQuantidadeEstoque(15);

        assertEquals(15, p.getQuantidadeEstoque());
    }

    // ---------- sincronizarEstoqueTotal() ----------

    @Test
    void deveSincronizarEstoqueTotalComBaseNaSomaDasVariacoes() {
        Peca p = criarPeca(0);
        p.setVariacoes(List.of(criarVariacao(4), criarVariacao(6)));

        p.sincronizarEstoqueTotal();

        assertEquals(10, p.getQuantidadeEstoque());
    }

    @Test
    void naoDeveAlterarEstoqueAoSincronizarQuandoNaoHaVariacoes() {
        Peca p = criarPeca(7);

        p.sincronizarEstoqueTotal();

        assertEquals(7, p.getQuantidadeEstoque());
    }

    // ---------- getImagemPrincipal() ----------

    @Test
    void deveRetornarLinkDaImagemMarcadaComoPrincipal() {
        peca.setLinkFoto("https://cdn.exemplo.com/fallback.jpg");
        peca.setImagens(List.of(
                criarImagem("https://cdn.exemplo.com/secundaria.jpg", 0),
                criarImagem("https://cdn.exemplo.com/principal.jpg", 1)
        ));

        assertEquals("https://cdn.exemplo.com/principal.jpg", peca.getImagemPrincipal());
    }

    @Test
    void deveRetornarLinkFotoComoFallbackQuandoNenhumaImagemForPrincipal() {
        peca.setLinkFoto("https://cdn.exemplo.com/fallback.jpg");
        peca.setImagens(List.of(criarImagem("https://cdn.exemplo.com/secundaria.jpg", 0)));

        assertEquals("https://cdn.exemplo.com/fallback.jpg", peca.getImagemPrincipal());
    }

    @Test
    void deveRetornarLinkFotoComoFallbackQuandoNaoHaImagens() {
        peca.setLinkFoto("https://cdn.exemplo.com/fallback.jpg");
        peca.setImagens(List.of());

        assertEquals("https://cdn.exemplo.com/fallback.jpg", peca.getImagemPrincipal());
    }
}