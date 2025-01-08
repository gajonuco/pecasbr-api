package com.sistema_restful.oficina_mecanica;
import com.sistema_restful.oficina_mecanica.model.Servico;
import com.sistema_restful.oficina_mecanica.repo.ServicoRepository;
import com.sistema_restful.oficina_mecanica.service.ServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void salvarServico_deveSalvarServico() {
        Servico servico = new Servico();
        servico.setNome("Balanceamento de Rodas");
        when(servicoRepository.save(servico)).thenReturn(servico);

        Servico resultado = servicoService.salvarServico(servico);
        assertEquals("Balanceamento de Rodas", resultado.getNome());
        verify(servicoRepository, times(1)).save(servico);
    }

    @Test
    void buscarServicoPorId_servicoExistente() {
        Servico servico = new Servico();
        servico.setId(1L);
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));

        Optional<Servico> resultado = servicoService.buscarServicoPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void deletarServico_deveDeletarServico() {
        servicoService.deletarServico(1L);
        verify(servicoRepository, times(1)).deleteById(1L);
    }
}
