package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.ClienteDAO;
import com.gajonuco.pecasbr.dao.PecaDAO;
import com.gajonuco.pecasbr.dao.PedidoDAO;
import com.gajonuco.pecasbr.dto.FiltroPedidoDTO;
import com.gajonuco.pecasbr.integration.dto.DTOResponse;
import com.gajonuco.pecasbr.integration.service.IAsaasService;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.ItemPedido;
import com.gajonuco.pecasbr.model.Peca;
import com.gajonuco.pecasbr.model.Pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceImplTest {

    @Mock
    private PedidoDAO dao;
    @Mock
    private PecaDAO pecaDAO;
    @Mock
    private ClienteDAO clienteDao;
    @Mock
    private IBotService botService;
    @Mock
    private IAsaasService asaasService;
    @Mock
    private NotificationService notificationService;

    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void setUp() {
        pedidoService = new PedidoServiceImpl();
        ReflectionTestUtils.setField(pedidoService, "dao", dao);
        ReflectionTestUtils.setField(pedidoService, "pecaDAO", pecaDAO);
        ReflectionTestUtils.setField(pedidoService, "clienteDao", clienteDao);
        ReflectionTestUtils.setField(pedidoService, "botService", botService);
        ReflectionTestUtils.setField(pedidoService, "asaasService", asaasService);
        ReflectionTestUtils.setField(pedidoService, "notificationService", notificationService);

        // Simula o comportamento real do JPA (@GeneratedValue IDENTITY): o id só
        // existe depois do primeiro save(). Sem isso, novo.getId() seria null e
        // a chamada ao Asaas (que espera um "int" primitivo) quebraria com NPE
        // antes mesmo do cenário que queremos testar começar.
        lenient().when(dao.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            if (p.getId() == null) {
                p.setId(1);
            }
            return p;
        });
    }

    // ---------- helpers de fixture ----------

    private Peca criarPeca(int quantidadeEstoque, int estoqueMinimo, int estoqueCritico, double preco, double precoPromo) {
        Peca peca = new Peca();
        peca.setId(1);
        peca.setNome("Peça Teste");
        peca.setQuantidadeEstoque(quantidadeEstoque);
        peca.setEstoqueMinimo(estoqueMinimo);
        peca.setEstoqueCritico(estoqueCritico);
        peca.setPreco(preco);
        peca.setPrecoPromo(precoPromo);
        return peca;
    }

    private ItemPedido criarItem(Peca peca, int qtd) {
        ItemPedido item = new ItemPedido();
        item.setPeca(peca);
        item.setQtdtItem(qtd);
        return item;
    }

    private Pedido criarPedidoComItens(ItemPedido... itens) {
        Pedido pedido = new Pedido();
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Teste");
        pedido.setCliente(cliente);
        pedido.setItensPedido(new ArrayList<>(List.of(itens)));
        return pedido;
    }

    private DTOResponse criarDtoResponse(String id, String invoiceUrl) {
        // Só id() e invoiceUrl() são lidos pelo PedidoServiceImpl; o resto é
        // preenchimento pra satisfazer o construtor do record.
        return new DTOResponse(
                id,
                "Plano Teste",
                99.9,
                true,
                "RECURRENT",
                invoiceUrl,
                "PIX",
                "MONTHLY",
                "Assinatura de teste",
                LocalDate.of(2027, 1, 1),
                false,
                10,
                3,
                5,
                true
        );
    }

    // =========================================================
    // inserirPedido
    // =========================================================

    @Test
    void deveInserirPedidoComSucessoQuandoEstoqueForSuficiente() {
        Peca peca = criarPeca(50, 10, 3, 100.0, 0);
        ItemPedido item = criarItem(peca, 5);
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(criarDtoResponse("pay_abc", "https://asaas.com/i/abc"));

        Pedido resultado = pedidoService.inserirPedido(novo);

        assertNotNull(resultado);
        assertEquals(Pedido.NOVO_PEDIDO, resultado.getStatus());
        assertEquals("https://asaas.com/i/abc", resultado.getLinkPagamento());
        assertEquals("pay_abc", resultado.getAsaasPaymentId());
        assertEquals(45, peca.getQuantidadeEstoque()); // 50 - 5
        assertEquals(100.0, item.getPrecoUnitario());
        assertEquals(500.0, item.getPrecoTotal());
        verify(pecaDAO, times(1)).save(peca);
        verify(dao, times(2)).save(novo); // salva antes e depois de gerar o link
    }

    @Test
    void deveUsarPrecoPromocionalQuandoDisponivel() {
        Peca peca = criarPeca(50, 10, 3, 100.0, 79.90);
        ItemPedido item = criarItem(peca, 2);
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(criarDtoResponse("pay_x", "https://asaas.com/i/x"));

        pedidoService.inserirPedido(novo);

        assertEquals(79.90, item.getPrecoUnitario());
        assertEquals(159.80, item.getPrecoTotal(), 0.001);
    }

    @Test
    void deveRetornarNuloSemAlterarEstoqueQuandoQuantidadeForMaiorQueODisponivel() {
        Peca peca = criarPeca(2, 10, 3, 100.0, 0);
        ItemPedido item = criarItem(peca, 5);
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));

        Pedido resultado = pedidoService.inserirPedido(novo);

        assertNull(resultado);
        assertEquals(2, peca.getQuantidadeEstoque()); // estoque não foi tocado
        verify(pecaDAO, never()).save(any());
        verify(dao, never()).save(any());
        verify(asaasService, never()).createPaymentLink(anyDouble(), any(), anyInt());
    }

    @Test
    void deveMarcarPecaComoIndisponivelQuandoEstoqueZerarAposVenda() {
        Peca peca = criarPeca(5, 10, 3, 100.0, 0);
        ItemPedido item = criarItem(peca, 5); // vende exatamente o que sobrou
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(criarDtoResponse("pay_y", "https://asaas.com/i/y"));

        pedidoService.inserirPedido(novo);

        assertEquals(0, peca.getQuantidadeEstoque());
        assertEquals(0, peca.getDisponivel());
        verify(notificationService, times(1)).notificarEstoqueZerado(peca);
        verify(notificationService, never()).notificarEstoqueBaixo(any());
        verify(notificationService, never()).notificarEstoqueCritico(any());
    }

    @Test
    void deveNotificarEstoqueBaixoQuandoTransicionarDeNormalParaBaixo() {
        Peca peca = criarPeca(15, 10, 3, 100.0, 0); // NORMAL (15 > 10)
        ItemPedido item = criarItem(peca, 6); // fica em 9 -> BAIXO (<=10 e >3)
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(criarDtoResponse("pay_z", "https://asaas.com/i/z"));

        pedidoService.inserirPedido(novo);

        assertEquals(9, peca.getQuantidadeEstoque());
        verify(notificationService, times(1)).notificarEstoqueBaixo(peca);
        verify(notificationService, never()).notificarEstoqueCritico(any());
        verify(notificationService, never()).notificarEstoqueZerado(any());
    }

    @Test
    void deveNotificarEstoqueCriticoQuandoTransicionarDeBaixoParaCritico() {
        Peca peca = criarPeca(5, 10, 3, 100.0, 0); // BAIXO (5 <=10 e >3)
        ItemPedido item = criarItem(peca, 3); // fica em 2 -> CRITICO (<=3 e >0)
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(criarDtoResponse("pay_w", "https://asaas.com/i/w"));

        pedidoService.inserirPedido(novo);

        assertEquals(2, peca.getQuantidadeEstoque());
        verify(notificationService, times(1)).notificarEstoqueCritico(peca);
    }

    @Test
    void naoDeveNotificarQuandoStatusDeEstoqueNaoMudarDeCategoria() {
        Peca peca = criarPeca(50, 10, 3, 100.0, 0); // NORMAL
        ItemPedido item = criarItem(peca, 5); // fica em 45, continua NORMAL
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(criarDtoResponse("pay_v", "https://asaas.com/i/v"));

        pedidoService.inserirPedido(novo);

        verifyNoInteractions(notificationService);
    }

    @Test
    void deveManterPedidoOrfaoQuandoAsaasNaoRetornaLinkDePagamento() {
        // Documenta um risco do código atual: se o Asaas devolver null, a
        // chamada dtoResponse.invoiceUrl() lança NPE, capturada pelo catch
        // genérico — mas o pedido e o desconto de estoque JÁ foram salvos
        // antes disso. O método retorna null como se nada tivesse acontecido,
        // porém o pedido continua no banco sem link de pagamento (órfão).
        Peca peca = criarPeca(50, 10, 3, 100.0, 0);
        ItemPedido item = criarItem(peca, 5);
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.of(peca));
        when(asaasService.createPaymentLink(anyDouble(), any(Cliente.class), anyInt()))
                .thenReturn(null);

        Pedido resultado = pedidoService.inserirPedido(novo);

        assertNull(resultado);
        assertEquals(45, peca.getQuantidadeEstoque()); // estoque já foi descontado
        verify(pecaDAO, times(1)).save(peca);            // e já foi salvo
        verify(dao, times(1)).save(novo);                // pedido salvo 1x, não 2x
    }

    @Test
    void deveRetornarNuloQuandoPecaDoItemNaoForEncontrada() {
        ItemPedido item = criarItem(criarPeca(50, 10, 3, 100.0, 0), 5);
        Pedido novo = criarPedidoComItens(item);

        when(pecaDAO.findById(1)).thenReturn(Optional.empty());

        Pedido resultado = pedidoService.inserirPedido(novo);

        assertNull(resultado);
        verify(dao, never()).save(any());
    }

    // =========================================================
    // mudarStatus
    // =========================================================

    @Test
    void deveMudarStatusComSucesso() {
        Pedido pedido = criarPedidoComItens();
        pedido.setId(7);
        pedido.setStatus(Pedido.NOVO_PEDIDO);
        when(dao.findById(7)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoService.mudarStatus(7, Pedido.PAGO);

        assertEquals(Pedido.PAGO, resultado.getStatus());
        verify(dao).save(pedido);
    }

    @Test
    void deveRetornarNuloQuandoPedidoNaoExistirAoMudarStatus() {
        when(dao.findById(99)).thenReturn(Optional.empty());

        Pedido resultado = pedidoService.mudarStatus(99, Pedido.PAGO);

        assertNull(resultado);
        verify(dao, never()).save(any());
    }

    // =========================================================
    // filtrarPorVariosCriterios — cobertura das 8 combinações
    // (temData x temNome x temStatus)
    // =========================================================

    @Test
    void deveBuscarNaoCanceladosQuandoNenhumCriterioForInformado() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(dao.findAllByStatusNotOrderByDataPedidoDesc()).thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        verifyNoInteractions(clienteDao);
    }

    @Test
    void deveBuscarPorClienteQuandoApenasNomeForInformado() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setNome("Maria");
        ArrayList<Cliente> clientes = new ArrayList<>(List.of(new Cliente()));
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(clienteDao.findByNomeContaining("Maria")).thenReturn(clientes);
        when(dao.findAllByClienteIn(clientes)).thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        verify(dao, never()).findAllByStatusNotOrderByDataPedidoDesc();
    }

    @Test
    @SuppressWarnings("unchecked")
    void deveBuscarPorStatusQuandoApenasStatusForInformado() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setPago(1);
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(dao.findAllByStatusInOrderByIdDesc(anyCollection())).thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        ArgumentCaptor<Collection<Integer>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(dao).findAllByStatusInOrderByIdDesc(captor.capture());
        assertEquals(List.of(Pedido.PAGO), new ArrayList<>(captor.getValue()));
        verifyNoInteractions(clienteDao);
    }

    @Test
    @SuppressWarnings("unchecked")
    void deveBuscarPorClienteEStatusQuandoNomeEStatusForemInformadosSemData() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setNome("Maria");
        filtro.setCancelado(1);
        ArrayList<Cliente> clientes = new ArrayList<>(List.of(new Cliente()));
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(clienteDao.findByNomeContaining("Maria")).thenReturn(clientes);
        when(dao.findAllByClienteInAndStatusIn(eq(clientes), anyCollection())).thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        ArgumentCaptor<Collection<Integer>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(dao).findAllByClienteInAndStatusIn(eq(clientes), captor.capture());
        assertEquals(List.of(Pedido.CANCELADO), new ArrayList<>(captor.getValue()));
    }

    @Test
    void deveBuscarPorPeriodoQuandoApenasDataForInformada() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setDataInicio(LocalDate.of(2026, 1, 1));
        filtro.setDataFim(LocalDate.of(2026, 1, 31));
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(dao.findAllByDataPedidoBetweenOrderByIdDesc(filtro.getDataInicio(), filtro.getDataFim()))
                .thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        verifyNoInteractions(clienteDao);
    }

    @Test
    @SuppressWarnings("unchecked")
    void deveBuscarPorPeriodoEStatusQuandoDataEStatusForemInformadosSemNome() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setDataInicio(LocalDate.of(2026, 1, 1));
        filtro.setDataFim(LocalDate.of(2026, 1, 31));
        filtro.setEntregue(1);
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(dao.findAllByDataPedidoBetweenAndStatusInOrderByIdDesc(
                eq(filtro.getDataInicio()), eq(filtro.getDataFim()), anyCollection()))
                .thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        ArgumentCaptor<Collection<Integer>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(dao).findAllByDataPedidoBetweenAndStatusInOrderByIdDesc(
                eq(filtro.getDataInicio()), eq(filtro.getDataFim()), captor.capture());
        assertEquals(List.of(Pedido.ENTREGUE), new ArrayList<>(captor.getValue()));
    }

    @Test
    void deveBuscarPorPeriodoEClienteQuandoDataENomeForemInformadosSemStatus() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setDataInicio(LocalDate.of(2026, 1, 1));
        filtro.setDataFim(LocalDate.of(2026, 1, 31));
        filtro.setNome("Maria");
        ArrayList<Cliente> clientes = new ArrayList<>(List.of(new Cliente()));
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(clienteDao.findByNomeContaining("Maria")).thenReturn(clientes);
        when(dao.findAllByDataPedidoBetweenAndClienteInOrderByIdDesc(
                filtro.getDataInicio(), filtro.getDataFim(), clientes))
                .thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
    }

    @Test
    @SuppressWarnings("unchecked")
    void deveBuscarPorPeriodoClienteEStatusQuandoTodosOsCriteriosForemInformados() {
        FiltroPedidoDTO filtro = new FiltroPedidoDTO();
        filtro.setDataInicio(LocalDate.of(2026, 1, 1));
        filtro.setDataFim(LocalDate.of(2026, 1, 31));
        filtro.setNome("Maria");
        filtro.setPago(1);
        ArrayList<Cliente> clientes = new ArrayList<>(List.of(new Cliente()));
        ArrayList<Pedido> esperado = new ArrayList<>(List.of(new Pedido()));
        when(clienteDao.findByNomeContaining("Maria")).thenReturn(clientes);
        when(dao.findAllByDataPedidoBetweenAndClienteInAndStatusInOrderByIdDesc(
                eq(filtro.getDataInicio()), eq(filtro.getDataFim()), eq(clientes), anyCollection()))
                .thenReturn(esperado);

        ArrayList<Pedido> resultado = pedidoService.filtrarPorVariosCriterios(filtro);

        assertSame(esperado, resultado);
        ArgumentCaptor<Collection<Integer>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(dao).findAllByDataPedidoBetweenAndClienteInAndStatusInOrderByIdDesc(
                eq(filtro.getDataInicio()), eq(filtro.getDataFim()), eq(clientes), captor.capture());
        assertEquals(List.of(Pedido.PAGO), new ArrayList<>(captor.getValue()));
    }

    // =========================================================
    // Delegações simples ao DAO — protegem contra erro de "copy-paste"
    // (ex: chamar o método de busca errado por engano)
    // =========================================================

    @Test
    void buscarStatusDeveDelegarParaDao() {
        ArrayList<Pedido> esperado = new ArrayList<>();
        when(dao.findAllByStatusOrderByDataPedidoDesc(Pedido.PAGO)).thenReturn(esperado);

        assertSame(esperado, pedidoService.buscarStatus(Pedido.PAGO));
    }

    @Test
    void buscarPorPeriodoDeveDelegarParaDao() {
        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 31);
        ArrayList<Pedido> esperado = new ArrayList<>();
        when(dao.findAllByDataPedidoBetween(inicio, fim)).thenReturn(esperado);

        assertSame(esperado, pedidoService.buscarPorPeriodo(inicio, fim));
    }

    @Test
    void buscarTodosDeveDelegarParaDao() {
        ArrayList<Pedido> esperado = new ArrayList<>();
        when(dao.findAllByOrderByDataPedidoDesc()).thenReturn(esperado);

        assertSame(esperado, pedidoService.buscarTodos());
    }

    @Test
    void buscarNaoCanceladosDeveDelegarParaDao() {
        ArrayList<Pedido> esperado = new ArrayList<>();
        when(dao.findAllByStatusNotOrderByDataPedidoDesc()).thenReturn(esperado);

        assertSame(esperado, pedidoService.buscarNaoCancelados());
    }

    @Test
    void buscarPeloIdDeveDelegarParaDao() {
        Pedido pedido = criarPedidoComItens();
        when(dao.findById(5)).thenReturn(Optional.of(pedido));

        assertSame(pedido, pedidoService.buscarPeloId(5));
    }

    @Test
    void atualizarPedidoDeveVincularItensAoPedidoAntesDeSalvar() {
        ItemPedido item1 = criarItem(criarPeca(10, 5, 2, 50.0, 0), 1);
        ItemPedido item2 = criarItem(criarPeca(10, 5, 2, 50.0, 0), 1);
        Pedido pedido = criarPedidoComItens(item1, item2);

        Pedido resultado = pedidoService.atualizarPedido(pedido);

        assertSame(pedido, resultado);
        assertSame(pedido, item1.getPedido());
        assertSame(pedido, item2.getPedido());
    }
}