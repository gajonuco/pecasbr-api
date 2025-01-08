package com.sistema_restful.oficina_mecanica;

import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.repo.ClienteRepository;
import com.sistema_restful.oficina_mecanica.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarCliente_deveSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("João da Silva");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.salvarCliente(cliente);
        assertEquals("João da Silva", resultado.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void buscarClientePorId_clienteExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.buscarClientePorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void deletarCliente_deveDeletarCliente() {
        clienteService.deletarCliente(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
