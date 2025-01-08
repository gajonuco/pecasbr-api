package com.sistema_restful.oficina_mecanica;

import com.sistema_restful.oficina_mecanica.model.Peca;
import com.sistema_restful.oficina_mecanica.repo.PecaRepository;
import com.sistema_restful.oficina_mecanica.service.PecaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PecaServiceTest {

    @Mock
    private PecaRepository pecaRepository;

    @InjectMocks
    private PecaService pecaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarPeca_deveSalvarPeca() {
        Peca peca = new Peca();
        peca.setNome("Eixo de Transmissão");
        when(pecaRepository.save(peca)).thenReturn(peca);

        Peca resultado = pecaService.salvarPeca(peca);
        assertEquals("Eixo de Transmissão", resultado.getNome());
        verify(pecaRepository, times(1)).save(peca);
    }

    @Test
    void buscarPecaPorId_pecaExistente() {
        Peca peca = new Peca();
        peca.setId(1L);
        when(pecaRepository.findById(1L)).thenReturn(Optional.of(peca));

        Optional<Peca> resultado = pecaService.buscarPecaPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void deletarPeca_deveDeletarPeca() {
        pecaService.deletarPeca(1L);
        verify(pecaRepository, times(1)).deleteById(1L);
    }
}
