package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.Servico;
import com.sistema_restful.oficina_mecanica.repository.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarServico_ComPrecoValido_DeveSalvarServico() {
        Servico servico = new Servico();
        servico.setNome("Troca de óleo");
        servico.setPreco(100.0);

        when(servicoRepository.save(servico)).thenReturn(servico);

        Servico resultado = servicoService.salvarServico(servico);

        assertNotNull(resultado);
        assertEquals("Troca de óleo", resultado.getNome());
        verify(servicoRepository, times(1)).save(servico);
    }

    @Test
    void salvarServico_ComPrecoAbaixoDoMinimo_DeveLancarExcecao() {
        Servico servico = new Servico();
        servico.setNome("Lavagem");
        servico.setPreco(30.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> servicoService.salvarServico(servico));
        assertEquals("O preço mínimo para um serviço é R$50.00.", exception.getMessage());
        verify(servicoRepository, never()).save(servico);
    }

    @Test
    void listarServicos_DeveRetornarPaginaDeServicos() {
        Pageable pageable = PageRequest.of(0, 10);
        Servico servico1 = new Servico();
        servico1.setNome("Alinhamento");
        Servico servico2 = new Servico();
        servico2.setNome("Balanceamento");

        Page<Servico> paginaMock = new PageImpl<>(Arrays.asList(servico1, servico2));
        when(servicoRepository.findAll(pageable)).thenReturn(paginaMock);

        Page<Servico> resultado = servicoService.listarServicos(pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        verify(servicoRepository, times(1)).findAll(pageable);
    }

    @Test
    void listarServicosEspecificos_ComIdsValidos_DeveRetornarLista() {
        List<Long> ids = Arrays.asList(1L, 2L);
        Servico servico1 = new Servico();
        servico1.setId(1L);
        Servico servico2 = new Servico();
        servico2.setId(2L);

        when(servicoRepository.findAllById(ids)).thenReturn(Arrays.asList(servico1, servico2));

        List<Servico> resultado = servicoService.listarServicosEspecificos(ids);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(servicoRepository, times(1)).findAllById(ids);
    }

    @Test
    void listarServicosEspecificos_ComIdsInvalidos_DeveLancarExcecao() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(servicoRepository.findAllById(ids)).thenReturn(Arrays.asList(new Servico()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> servicoService.listarServicosEspecificos(ids));
        assertEquals("Alguns IDs fornecidos não correspondem a serviços existentes.", exception.getMessage());
        verify(servicoRepository, times(1)).findAllById(ids);
    }

    @Test
    void atualizarServico_ComDadosValidos_DeveAtualizarServico() {
        Long id = 1L;
        Servico servicoExistente = new Servico();
        servicoExistente.setId(id);
        servicoExistente.setNome("Alinhamento");
        servicoExistente.setPreco(100.0);

        Servico servicoAtualizado = new Servico();
        servicoAtualizado.setNome("Alinhamento e balanceamento");
        servicoAtualizado.setPreco(150.0);

        when(servicoRepository.findById(id)).thenReturn(Optional.of(servicoExistente));
        when(servicoRepository.save(servicoExistente)).thenReturn(servicoExistente);

        Servico resultado = servicoService.atualizarServico(id, servicoAtualizado);

        assertNotNull(resultado);
        assertEquals("Alinhamento e balanceamento", resultado.getNome());
        assertEquals(150.0, resultado.getPreco());
        verify(servicoRepository, times(1)).findById(id);
        verify(servicoRepository, times(1)).save(servicoExistente);
    }

    @Test
    void atualizarServico_ComIdInvalido_DeveLancarExcecao() {
        Long id = 1L;
        Servico servicoAtualizado = new Servico();

        when(servicoRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> servicoService.atualizarServico(id, servicoAtualizado));
        assertEquals("Serviço com ID 1 não encontrado.", exception.getMessage());
        verify(servicoRepository, times(1)).findById(id);
        verify(servicoRepository, never()).save(any());
    }

    @Test
    void deletarServico_ComIdExistente_DeveDeletarServico() {
        Long id = 1L;

        when(servicoRepository.existsById(id)).thenReturn(true);

        servicoService.deletarServico(id);

        verify(servicoRepository, times(1)).existsById(id);
        verify(servicoRepository, times(1)).deleteById(id);
    }

    @Test
    void deletarServico_ComIdInexistente_DeveLancarExcecao() {
        Long id = 1L;

        when(servicoRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> servicoService.deletarServico(id));
        assertEquals("Serviço com ID 1 não encontrado.", exception.getMessage());
        verify(servicoRepository, times(1)).existsById(id);
        verify(servicoRepository, never()).deleteById(id);
    }
}
