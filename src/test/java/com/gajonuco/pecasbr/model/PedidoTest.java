package com.gajonuco.pecasbr.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void deveCriarPedidoParaRetirada() {
        Cliente cliente = new Cliente();
        ItemPedido item = new ItemPedido();

        Pedido pedido = Pedido.novoPedido(
                cliente,
                null,
                true,
                new BigDecimal("15.00"),
                "Retirada na loja",
                List.of(item)
        );

        assertSame(cliente, pedido.getCliente());
        assertEquals(1, pedido.getRetirar());
        assertNull(pedido.getEnderecoEntrega());
        assertEquals(BigDecimal.ZERO, pedido.getValorFrete());
        assertEquals(LocalDate.now(), pedido.getDataPedido());
        assertSame(pedido, item.getPedido());
        assertSame(item, pedido.getItensPedido().get(0));
    }

    @Test
    void deveCriarPedidoComEntrega() {
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        ItemPedido item = new ItemPedido();
        BigDecimal frete = new BigDecimal("15.00");

        Pedido pedido = Pedido.novoPedido(
                cliente,
                endereco,
                false,
                frete,
                "Entregar no endereço cadastrado",
                List.of(item)
        );

        assertSame(cliente, pedido.getCliente());
        assertEquals(0, pedido.getRetirar());
        assertSame(endereco, pedido.getEnderecoEntrega());
        assertEquals(frete, pedido.getValorFrete());
        assertSame(pedido, item.getPedido());
    }

    @Test
    void deveDefinirFreteZeroQuandoValorForNulo() {
        Pedido pedido = Pedido.novoPedido(
                new Cliente(),
                new Endereco(),
                false,
                null,
                null,
                List.of(new ItemPedido())
        );

        assertEquals(BigDecimal.ZERO, pedido.getValorFrete());
    }

    @Test
    void naoDevePermitirClienteNulo() {
        assertThrows(NullPointerException.class, () ->
                Pedido.novoPedido(
                        null, null, true, null, null,
                        List.of(new ItemPedido())
                )
        );
    }

    @Test
    void naoDevePermitirListaDeItensNula() {
        assertThrows(NullPointerException.class, () ->
                Pedido.novoPedido(
                        new Cliente(), null, true, null, null, null
                )
        );
    }

    @Test
    void naoDevePermitirEntregaSemEndereco() {
        assertThrows(NullPointerException.class, () ->
                Pedido.novoPedido(
                        new Cliente(), null, false, null, null,
                        List.of(new ItemPedido())
                )
        );
    }
}
