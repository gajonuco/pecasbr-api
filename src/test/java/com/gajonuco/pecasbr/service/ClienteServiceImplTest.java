package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.ClienteDAO;
import com.gajonuco.pecasbr.dto.CadastroClienteDTO;
import com.gajonuco.pecasbr.dto.LoginClienteDTO;
import com.gajonuco.pecasbr.exception.ClienteJaCadastradoException;
import com.gajonuco.pecasbr.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteDAO dao;

    private PasswordEncoder passwordEncoder;
    private ClienteServiceImpl service;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        service = new ClienteServiceImpl(dao, passwordEncoder);
    }

    private CadastroClienteDTO dadosCadastro() {
        return new CadastroClienteDTO("Maria","maria@teste.com", "senha123","12345678900","11999999999", LocalDate.of(1990,1,1));
    }

    @Test
    void deveCriarClienteNovoQuandoNaoExistirNemPorEmailNemPorCpf(){
        when(dao.findByEmail("maria@teste.com")).thenReturn(null);
        when(dao.findByCpf("12345678900")).thenReturn(null);
        when(dao.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Cliente resultado = service.cadastrar(dadosCadastro());

        assertTrue(passwordEncoder.matches("senha123", resultado.getSenha()));
        verify(dao).save(any(Cliente.class));
    }


    @Test
    void deveAtivarContaDeClienteGuestExistentePeloCpf(){
        Cliente guest = new Cliente();
        guest.setCpf("12345678900");
        // guest.getSenha() é null — só existe por causa de uma compra anterior

        when(dao.findByEmail("maria@teste.com")).thenReturn(null);
        when(dao.findByCpf("12345678900")).thenReturn(guest);
        when(dao.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = service.cadastrar(dadosCadastro());

        assertSame(guest,resultado);
        assertNotNull(resultado.getSenha());
    }

    @Test
    void deveRecusarCadastroQuandoJaExistirContaComSenha(){
        Cliente existente = new Cliente();
        existente.setEmail("maria@teste.com");
        existente.setSenha(passwordEncoder.encode("outraSenha"));

        when(dao.findByEmail("maria@teste.com")).thenReturn(existente);

        assertThrows(ClienteJaCadastradoException.class,() ->service.cadastrar(dadosCadastro()));
        verify(dao, never()).save(any());
    }

    @Test
    void deveAutenticarComCredenciaisCorretas(){
        Cliente cliente = new Cliente();
        cliente.setEmail("maria@teste.com");
        cliente.setSenha(passwordEncoder.encode("senha123"));

        when(dao.findByEmail("maria@teste.com")).thenReturn(cliente);

        assertNotNull(service.autenticar(new LoginClienteDTO("maria@teste.com","senha123")));
    }

    @Test
    void deveRecusarAutenticacaoComSenhaIncorreta(){
        Cliente cliente = new Cliente();
        cliente.setEmail("maria@teste.com");
        cliente.setSenha(passwordEncoder.encode("senha123"));

        when(dao.findByEmail("maria@teste.com")).thenReturn(cliente);

        assertNull(service.autenticar(new LoginClienteDTO("maria@teste.com", "senhaErrada")));
    }

    @Test
    void deveRecusarAutenticacaoQuandoClienteAindaNaoTiverSenha(){
        Cliente guest = new Cliente();
        guest.setEmail("maria@teste.com"); // sem senha – nunca ativou conta

        when(dao.findByEmail("maria@teste.com")).thenReturn(guest);

        assertNull(service.autenticar(new LoginClienteDTO("maria@teste.com","qualquerCoisa")));
    }






    


}
