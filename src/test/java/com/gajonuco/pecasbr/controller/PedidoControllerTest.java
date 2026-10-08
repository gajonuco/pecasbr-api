package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.CriarPedidoDTO;
import com.gajonuco.pecasbr.dto.DadosClienteDTO;
import com.gajonuco.pecasbr.dto.EnderecoDTO;
import com.gajonuco.pecasbr.dto.ItemPedidoDTO;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Endereco;
import com.gajonuco.pecasbr.model.Pedido;
import com.gajonuco.pecasbr.service.IClienteService;
import com.gajonuco.pecasbr.service.IEnderecoService;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private IPedidoService service;
    @Mock
    private IClienteService cliService;
    @Mock
    private IEnderecoService enderecoService;
    @Mock
    private Authentication authentication;

    private PedidoController controller;

    @BeforeEach
    void setUp() {
        controller = new PedidoController(service, cliService, enderecoService);
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

    @Test
    void guestCriaPedidoComEnderecoNovo() {
        CriarPedidoDTO dados = new CriarPedidoDTO(
                new DadosClienteDTO("Maria Teste", "maria@teste.com", "12345678900", "11999999999", null),
                List.of(new ItemPedidoDTO(1, 2, null, null, null)),
                "sem observações", false, null,
                new EnderecoDTO(null, "89800-000", "Rua A", "100", null, "Centro", "Chapecó", "SC"));

        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setId(10);
        when(cliService.atualizarDados(any(Cliente.class))).thenReturn(clienteSalvo);

        Endereco enderecoCriado = new Endereco();
        enderecoCriado.setId(5);
        when(enderecoService.criar(eq(clienteSalvo), any(EnderecoDTO.class))).thenReturn(enderecoCriado);
        when(service.inserirPedido(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Pedido> resposta = controller.inserirNovoPedido(dados, null);

        assertEquals(201, resposta.getStatusCode().value());
        assertEquals(enderecoCriado, resposta.getBody().getEnderecoEntrega());
    }

    @Test
    void clienteLogadoCriaPedidoReferenciandoEnderecoSalvo() {
        CriarPedidoDTO dados = new CriarPedidoDTO(
                null, List.of(new ItemPedidoDTO(1, 1, null, null, null)), null, false, 7, null);

        Authentication auth = mock(Authentication.class);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))).when(auth).getAuthorities();
        when(auth.getName()).thenReturn("maria@teste.com");

        Cliente cliente = new Cliente();
        cliente.setId(10);
        when(cliService.buscarPeloEmail("maria@teste.com")).thenReturn(cliente);

        Endereco endereco = new Endereco();
        endereco.setId(7);
        when(enderecoService.buscarPorIdDoCliente(cliente, 7)).thenReturn(endereco);
        when(service.inserirPedido(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Pedido> resposta = controller.inserirNovoPedido(dados, auth);

        assertEquals(201, resposta.getStatusCode().value());
        assertEquals(endereco, resposta.getBody().getEnderecoEntrega());
    }

    @Test
    void pedidoParaRetirarNaoExigeEndereco() {
        CriarPedidoDTO dados = new CriarPedidoDTO(
                new DadosClienteDTO("Maria", "maria@teste.com", "12345678900", "11999999999", null),
                List.of(new ItemPedidoDTO(1, 1, null, null, null)), null, true, null, null);

        when(cliService.atualizarDados(any(Cliente.class))).thenReturn(new Cliente());
        when(service.inserirPedido(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<Pedido> resposta = controller.inserirNovoPedido(dados, null);

        assertEquals(201, resposta.getStatusCode().value());
        assertNull(resposta.getBody().getEnderecoEntrega());
        verifyNoInteractions(enderecoService);
    }

    @Test
    void guestSemEnderecoNemIdRetorna400() {
        CriarPedidoDTO dados = new CriarPedidoDTO(
                new DadosClienteDTO("Maria", "maria@teste.com", "12345678900", "11999999999", null),
                List.of(new ItemPedidoDTO(1, 1, null, null, null)), null, false, null, null);
        when(cliService.atualizarDados(any(Cliente.class))).thenReturn(new Cliente());

        ResponseEntity<Pedido> resposta = controller.inserirNovoPedido(dados, null);

        assertEquals(400, resposta.getStatusCode().value());
    }



}
