package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.repository.PecaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PecaServiceTest {

    @InjectMocks
    private PecaService pecaService;

    @Mock
    private PecaRepository pecaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarPeca_ComDadosValidos_DeveSalvarPeca() {
        Peca peca = new Peca();
        peca.setNome("Filtro de óleo");
        peca.setPreco(50.0);

        when(pecaRepository.save(peca)).thenReturn(peca);

        Peca pecaSalva = pecaService.salvarPeca(peca);

        assertNotNull(pecaSalva);
        assertEquals("Filtro de óleo", pecaSalva.getNome());
        assertEquals(50.0, pecaSalva.getPreco());
        verify(pecaRepository, times(1)).save(peca);
    }

    @Test
    void salvarPeca_ComNomeInvalido_DeveLancarExcecao() {
        Peca peca = new Peca();
        peca.setNome("");
        peca.setPreco(50.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> pecaService.salvarPeca(peca));

        assertEquals("O nome da peça é obrigatório.", exception.getMessage());
        verify(pecaRepository, never()).save(any(Peca.class));
    }

    @Test
    void listarPecas_DeveRetornarPaginaDePecas() {
        Pageable pageable = PageRequest.of(0, 10);
        Peca peca1 = new Peca();
        peca1.setId(1L);
        peca1.setNome("Pneu");
        peca1.setPreco(200.0);

        Peca peca2 = new Peca();
        peca2.setId(2L);
        peca2.setNome("Filtro de ar");
        peca2.setPreco(30.0);

        Page<Peca> page = new PageImpl<>(List.of(peca1, peca2));
        when(pecaRepository.findAll(pageable)).thenReturn(page);

        Page<Peca> pecas = pecaService.listarPecas(pageable);

        assertNotNull(pecas);
        assertEquals(2, pecas.getContent().size());
        verify(pecaRepository, times(1)).findAll(pageable);
    }

    @Test
    void atualizarPeca_ComDadosValidos_DeveAtualizarPeca() {
        Peca pecaExistente = new Peca();
        pecaExistente.setId(1L);
        pecaExistente.setNome("Pneu");
        pecaExistente.setPreco(200.0);

        Peca pecaAtualizada = new Peca();
        pecaAtualizada.setNome("Pneu Premium");
        pecaAtualizada.setPreco(250.0);

        when(pecaRepository.findById(1L)).thenReturn(Optional.of(pecaExistente));
        when(pecaRepository.save(pecaExistente)).thenReturn(pecaExistente);

        Peca pecaSalva = pecaService.atualizarPeca(1L, pecaAtualizada);

        assertNotNull(pecaSalva);
        assertEquals("Pneu Premium", pecaSalva.getNome());
        assertEquals(250.0, pecaSalva.getPreco());
        verify(pecaRepository, times(1)).findById(1L);
        verify(pecaRepository, times(1)).save(pecaExistente);
    }

    @Test
    void atualizarPeca_ComIdInvalido_DeveLancarExcecao() {
        Peca pecaAtualizada = new Peca();
        pecaAtualizada.setNome("Pneu Premium");
        pecaAtualizada.setPreco(250.0);

        when(pecaRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> pecaService.atualizarPeca(1L, pecaAtualizada));

        assertEquals("Peça com ID 1 não encontrada.", exception.getMessage());
        verify(pecaRepository, times(1)).findById(1L);
        verify(pecaRepository, never()).save(any(Peca.class));
    }

    @Test
    void deletarPeca_ComIdValido_DeveDeletarPeca() {
        when(pecaRepository.existsById(1L)).thenReturn(true);

        pecaService.deletarPeca(1L);

        verify(pecaRepository, times(1)).existsById(1L);
        verify(pecaRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletarPeca_ComIdInvalido_DeveLancarExcecao() {
        when(pecaRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> pecaService.deletarPeca(1L));

        assertEquals("Peça com ID 1 não encontrada.", exception.getMessage());
        verify(pecaRepository, times(1)).existsById(1L);
        verify(pecaRepository, never()).deleteById(anyLong());
    }
}
