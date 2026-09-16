package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Pedido;
import com.gajonuco.pecasbr.service.IClienteService;
import com.gajonuco.pecasbr.service.IPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private IPedidoService service;
    @Mock
    private IClienteService cliService;
    @Mock
    private Authentication authentication;

    private PedidoController controller;

    @BeforeEach
    void setUp() {
        controller = new PedidoController(service, cliService);
    }

    private Pedido pedidoDoCliente(String emailDono) {
        Cliente dono = new Cliente();
        dono.setEmail(emailDono);
        Pedido pedido = new Pedido();
        pedido.setCliente(dono);
        return pedido;
    }

    @Test
    void clienteDonoAcessaProprioPedido(){
        when(service.buscarPeloId(1)).thenReturn(pedidoDoCliente("maria@teste.com"));
        when(authentication.getName()).thenReturn("maria@teste.com");
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))).when(authentication).getAuthorities();

        ResponseEntity<Pedido> resposta = controller.recuperarPedido(1, authentication);

        assertEquals(200,resposta.getStatusCode().value());
    }

    @Test
    void clienteNaoDonoNaoAcessaPedidoDeOutroCliente(){
        when(service.buscarPeloId(1)).thenReturn(pedidoDoCliente("maria@teste.com"));
        when(authentication.getName()).thenReturn("outro@teste.com");
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))).when(authentication).getAuthorities();

        ResponseEntity<Pedido> resposta = controller.recuperarPedido(1,authentication);

        assertEquals(403,resposta.getStatusCode().value());

    }

    @Test
    void staffAcessaQualquerPedido(){
        when(service.buscarPeloId(1)).thenReturn(pedidoDoCliente("maria@teste.com"));
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_VENDEDOR"))).when(authentication).getAuthorities();

        ResponseEntity<Pedido> resposta = controller.recuperarPedido(1, authentication);

        assertEquals(200, resposta.getStatusCode().value());
    }

    @Test
    void retorna404QuandoPedidoNaoExiste(){
        when(service.buscarPeloId(99)).thenReturn(null);

        ResponseEntity<Pedido> resposta = controller.recuperarPedido(99, authentication);

        assertEquals(404,resposta.getStatusCode().value());
    }





}
