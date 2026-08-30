package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dao.PedidoDAO;
import com.gajonuco.pecasbr.integration.dto.DTOWebhookAsaas;
import com.gajonuco.pecasbr.model.Pedido;
import com.gajonuco.pecasbr.service.NotificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AsaasWebhookControllerTest {

    @Mock
    private PedidoDAO pedidoDAO;
    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @Mock
    private NotificationService notificationService;

    private AsaasWebhookController controller;

    @BeforeEach
    void setUp() {
        // Diferente do AsaasServiceImplTest, aqui NÃO existe cadeia fluente pra
        // reconstruir (RestClient) — o controller recebe suas 3 dependências direto
        // no construtor, então o setup é só instanciar com os mocks.
        controller = new AsaasWebhookController(pedidoDAO, messagingTemplate, notificationService);
    }

    // ---------- helpers de fixture ----------

    private Pedido criarPedido(int id, int status) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus(status);
        pedido.setAsaasPaymentId("pay_" + id);
        return pedido;
    }

    private DTOWebhookAsaas criarEvento(String event, String paymentId) {
        return new DTOWebhookAsaas(event, new DTOWebhookAsaas.Payment(paymentId, "CONFIRMED"));
    }

    // ---------- curto-circuitos: 200 sem tocar em nada ----------

    @Test
    void deveRetornar200SemConsultarBancoQuandoEventoForNulo() {
        DTOWebhookAsaas dto = new DTOWebhookAsaas(null, new DTOWebhookAsaas.Payment("pay_1", "CONFIRMED"));

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        verify(pedidoDAO, never()).findByAsaasPaymentId(anyString());
        verifyNoInteractions(notificationService, messagingTemplate);
    }

    @Test
    void deveRetornar200SemConsultarBancoQuandoPaymentForNulo() {
        DTOWebhookAsaas dto = new DTOWebhookAsaas("PAYMENT_CONFIRMED", null);

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        verify(pedidoDAO, never()).findByAsaasPaymentId(anyString());
        verifyNoInteractions(notificationService, messagingTemplate);
    }

    @Test
    void deveIgnorarEventosQueNaoSejamDePagamentoConfirmado() {
        // Ex: PAYMENT_OVERDUE, PAYMENT_DELETED, etc. — só PAYMENT_CONFIRMED e
        // PAYMENT_RECEIVED disparam o fluxo de confirmação.
        DTOWebhookAsaas dto = criarEvento("PAYMENT_OVERDUE", "pay_1");

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        verify(pedidoDAO, never()).findByAsaasPaymentId(anyString());
        verifyNoInteractions(notificationService, messagingTemplate);
    }

    // ---------- pedido não encontrado para o paymentId ----------

    @Test
    void deveRetornar200QuandoNenhumPedidoForEncontradoParaOPaymentId() {
        DTOWebhookAsaas dto = criarEvento("PAYMENT_CONFIRMED", "pay_inexistente");
        when(pedidoDAO.findByAsaasPaymentId("pay_inexistente")).thenReturn(Optional.empty());

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        verify(pedidoDAO, never()).save(any());
        verifyNoInteractions(notificationService, messagingTemplate);
    }

    // ---------- idempotência: o teste mais importante desta classe ----------

    @Test
    void naoDeveReprocessarNemNotificarQuandoPedidoJaEstiverPago() {
        // O Asaas pode reenviar o mesmo webhook (retry). Esse teste garante que
        // um pedido já confirmado não dispara notificação nem save duplicados.
        Pedido pedidoJaPago = criarPedido(10, Pedido.PAGO);
        DTOWebhookAsaas dto = criarEvento("PAYMENT_CONFIRMED", "pay_10");
        when(pedidoDAO.findByAsaasPaymentId("pay_10")).thenReturn(Optional.of(pedidoJaPago));

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        verify(pedidoDAO, never()).save(any());
        verifyNoInteractions(notificationService);
        verifyNoInteractions(messagingTemplate);
    }

    // ---------- fluxo de sucesso ----------

    @Test
    void deveConfirmarPagamentoQuandoEventoForPaymentConfirmed() {
        Pedido pedido = criarPedido(20, Pedido.NOVO_PEDIDO);
        DTOWebhookAsaas dto = criarEvento("PAYMENT_CONFIRMED", "pay_20");
        when(pedidoDAO.findByAsaasPaymentId("pay_20")).thenReturn(Optional.of(pedido));

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        assertEquals(Pedido.PAGO, pedido.getStatus());
        verify(pedidoDAO, times(1)).save(pedido);
        verify(notificationService, times(1)).notificarPagamentoConfirmado(pedido);
        verify(messagingTemplate, times(1))
                .convertAndSend("/topic/payment/" + pedido.getId(), pedido.getId());
    }

    @Test
    void deveConfirmarPagamentoQuandoEventoForPaymentReceived() {
        // Cobre o segundo evento aceito pela condição isPago — garante que a
        // regra "||" não foi testada só pelo lado que já sabemos que funciona.
        Pedido pedido = criarPedido(21, Pedido.NOVO_PEDIDO);
        DTOWebhookAsaas dto = criarEvento("PAYMENT_RECEIVED", "pay_21");
        when(pedidoDAO.findByAsaasPaymentId("pay_21")).thenReturn(Optional.of(pedido));

        ResponseEntity<Void> resposta = controller.receberEvento(dto);

        assertEquals(200, resposta.getStatusCode().value());
        assertEquals(Pedido.PAGO, pedido.getStatus());
        verify(pedidoDAO, times(1)).save(pedido);
        verify(notificationService, times(1)).notificarPagamentoConfirmado(pedido);
    }

    // ---------- comportamento a validar com o time de negócio ----------

    @Test
    void devePermitirTransicaoDeCanceladoParaPagoNoEstadoAtualDoCodigo() {
        // Este teste documenta o comportamento ATUAL do controller: a única
        // checagem de idempotência é "status == PAGO". Um pedido CANCELADO (ou
        // em qualquer outro status) que receba confirmação de pagamento depois
        // ainda assim é promovido para PAGO — não existe bloqueio para estados
        // "finais". Vale confirmar com o time se isso é intencional.
        Pedido pedidoCancelado = criarPedido(30, Pedido.CANCELADO);
        DTOWebhookAsaas dto = criarEvento("PAYMENT_CONFIRMED", "pay_30");
        when(pedidoDAO.findByAsaasPaymentId("pay_30")).thenReturn(Optional.of(pedidoCancelado));

        controller.receberEvento(dto);

        assertEquals(Pedido.PAGO, pedidoCancelado.getStatus());
        verify(notificationService, times(1)).notificarPagamentoConfirmado(pedidoCancelado);
    }
}