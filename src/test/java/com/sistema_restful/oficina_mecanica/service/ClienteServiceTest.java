package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.Cliente;
import com.sistema_restful.oficina_mecanica.model.Endereco;
import com.sistema_restful.oficina_mecanica.repo.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
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
    void salvarCliente_deveSalvarClienteValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");
        cliente.setTelefone("1234567890");
        cliente.setEndereco(new Endereco());

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente salvo = clienteService.salvarCliente(cliente);

        assertNotNull(salvo);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void salvarCliente_deveLancarExcecaoQuandoClienteNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteService.salvarCliente(null));

        assertEquals("Cliente não pode ser nulo.", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void salvarCliente_deveLancarExcecaoQuandoEmailDuplicado() {
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setEmail("duplicado@example.com");
        cliente.setTelefone("1234567890");

        when(clienteRepository.existsByEmail(cliente.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteService.salvarCliente(cliente));

        assertEquals("Já existe um cliente cadastrado com este e-mail.", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void listarClientes_deveRetornarPaginaDeClientes() {
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        Pageable pageable = PageRequest.of(0, 10);

        when(clienteRepository.findAll(pageable)).thenReturn(page);

        Page<Cliente> resultado = clienteService.listarClientes(0, 10, "nome");

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(clienteRepository, times(1)).findAll(pageable);
    }

    @Test
    void atualizarCliente_deveAtualizarClienteExistente() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setNome("Old Name");

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("New Name");
        clienteAtualizado.setTelefone("9876543210");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(clienteExistente)).thenReturn(clienteExistente);

        Cliente resultado = clienteService.atualizarCliente(1L, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("New Name", resultado.getNome());
        assertEquals("9876543210", resultado.getTelefone());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(clienteExistente);
    }

    @Test
    void atualizarCliente_deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> clienteService.atualizarCliente(1L, new Cliente()));

        assertEquals("Cliente com ID 1 não encontrado.", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void deletarCliente_deveDeletarClienteExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);

        clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    void deletarCliente_deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> clienteService.deletarCliente(1L));

        assertEquals("Cliente com ID 1 não encontrado.", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).delete(any());
    }
}
