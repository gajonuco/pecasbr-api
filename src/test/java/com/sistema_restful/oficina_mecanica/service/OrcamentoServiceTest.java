package com.sistema_restful.oficina_mecanica.service;

import com.sistema_restful.oficina_mecanica.dto.OrcamentoDTO;
import com.sistema_restful.oficina_mecanica.dto.OrcamentoResponseDTO;
import com.sistema_restful.oficina_mecanica.exception.ResourceNotFoundException;
import com.sistema_restful.oficina_mecanica.model.*;
import com.sistema_restful.oficina_mecanica.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class OrcamentoServiceTest {

    @InjectMocks
    private OrcamentoService orcamentoService;

    @Mock
    private OrcamentoRepository orcamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private PecaRepository pecaRepository;

    private Cliente cliente;
    private Servico servico;
    private Peca peca;
    private OrcamentoDTO orcamentoDTO;

    @BeforeEach
    void setup() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        servico = new Servico();
        servico.setId(1L);
        servico.setPreco(100.0);
        servico.setNome("Serviço Teste");

        peca = new Peca();
        peca.setId(1L);
        peca.setPreco(50.0);
        peca.setDescricao("Peça Teste");

        orcamentoDTO = new OrcamentoDTO(
                null,
                cliente.getId(),
                "Descrição Teste",
                0.0,
                List.of(new OrcamentoDTO.ServicoQuantidadeDTO(servico.getId(), 2)),
                List.of(new OrcamentoDTO.PecaQuantidadeDTO(peca.getId(), 3))
        );
    }

    @Test
    void salvarOrcamento_ComDadosValidos_DeveRetornarOrcamentoResponseDTO() {
        // Arrange
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(servicoRepository.findById(servico.getId())).thenReturn(Optional.of(servico));
        when(pecaRepository.findById(peca.getId())).thenReturn(Optional.of(peca));
        when(orcamentoRepository.save(any(Orcamento.class))).thenAnswer(invocation -> {
            Orcamento saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // Act
        OrcamentoResponseDTO response = orcamentoService.salvarOrcamento(orcamentoDTO);

        // Assert
        assertNotNull(response);
        assertEquals(cliente.getId(), response.getClienteId());
        assertEquals(1, response.getServicos().size());
        assertEquals(1, response.getPecas().size());
        assertEquals(350.0, response.getValorTotal());
        assertEquals(35.0, response.getValorDesconto());

        verify(clienteRepository).findById(cliente.getId());
        verify(servicoRepository).findById(servico.getId());
        verify(pecaRepository).findById(peca.getId());
        verify(orcamentoRepository).save(any(Orcamento.class));
    }

    @Test
    void salvarOrcamento_ComClienteNaoEncontrado_DeveLancarExcecao() {
        // Arrange
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                orcamentoService.salvarOrcamento(orcamentoDTO));

        assertEquals("Cliente não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).findById(cliente.getId());
        verifyNoInteractions(servicoRepository, pecaRepository, orcamentoRepository);
    }

    @Test
    void salvarOrcamento_ComServicoNaoEncontrado_DeveLancarExcecao() {
        // Arrange
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(servicoRepository.findById(servico.getId())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                orcamentoService.salvarOrcamento(orcamentoDTO));

        assertEquals("Serviço não encontrado com ID: 1", exception.getMessage());
        verify(clienteRepository).findById(cliente.getId());
        verify(servicoRepository).findById(servico.getId());
        verifyNoInteractions(pecaRepository, orcamentoRepository);
    }

    @Test
    void deletarOrcamento_ComIdValido_DeveExcluirOrcamento() {
        // Arrange
        when(orcamentoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orcamentoRepository).deleteById(1L);

        // Act
        orcamentoService.deletarOrcamento(1L);

        // Assert
        verify(orcamentoRepository).existsById(1L);
        verify(orcamentoRepository).deleteById(1L);
    }

    @Test
    void deletarOrcamento_ComIdInvalido_DeveLancarExcecao() {
        // Arrange
        when(orcamentoRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                orcamentoService.deletarOrcamento(1L));

        assertEquals("Orçamento com ID 1 não encontrado.", exception.getMessage());
        verify(orcamentoRepository).existsById(1L);
        verify(orcamentoRepository, never()).deleteById(anyLong());
    }
}
