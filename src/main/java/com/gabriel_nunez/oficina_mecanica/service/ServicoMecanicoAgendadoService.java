package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.request.ServicoMecanicoAgendadoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.ServicoMecanicoAgendadoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.enums.StatusServico;
import com.gabriel_nunez.oficina_mecanica.mapper.ServicoMecanicoAgendadoMapper;
import com.gabriel_nunez.oficina_mecanica.model.*;
import com.gabriel_nunez.oficina_mecanica.repository.*;
import com.gabriel_nunez.oficina_mecanica.user.User;
import com.gabriel_nunez.oficina_mecanica.user.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicoMecanicoAgendadoService {

    private final ServicoMecanicoAgendadoRepository servicoMecanicoAgendadoRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final PecaRepository pecaRepository;
    private final UserRepository userRepository;
    private final TipoServicoMecanicoRepository tipoServicoMecanicoRepository;
    private final ServicoMecanicoAgendadoMapper mapper;

    public ServicoMecanicoAgendadoService(ServicoMecanicoAgendadoRepository servicoMecanicoAgendadoRepository,
                                  ClienteRepository clienteRepository,
                                  VeiculoRepository veiculoRepository,
                                  PecaRepository pecaRepository,
                                  UserRepository userRepository,
                                 TipoServicoMecanicoRepository tipoServicoMecanicoRepository,
                                  ServicoMecanicoAgendadoMapper mapper) {
        this.servicoMecanicoAgendadoRepository = servicoMecanicoAgendadoRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.pecaRepository = pecaRepository;
        this.userRepository = userRepository;
        this.tipoServicoMecanicoRepository = tipoServicoMecanicoRepository;
        this.mapper = mapper;
    }

    public ServicoMecanicoAgendadoResponseDTO criarServico(ServicoMecanicoAgendadoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        TipoServicoMecanico tipoServico = tipoServicoMecanicoRepository.findById(dto.getTipoServicoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de serviço não encontrado"));

        List<Peca> pecas = pecaRepository.findAllById(dto.getPecasUtilizadasIds());
        List<User> mecanicos = userRepository.findAllById(dto.getMecanicosResponsaveisIds()).stream()
                .filter(user -> user.getRole() == UserRole.MECANICO)
                .toList();

        BigDecimal valorTotalPecas = pecas.stream()
                .map(Peca::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorTotal = tipoServico.getValorBase().add(valorTotalPecas);

        ServicoMecanicoAgendado servico = ServicoMecanicoAgendado.builder()
                .cliente(cliente)
                .veiculo(veiculo)
                .tipoServico(tipoServico)
                .pecasUtilizadas(pecas)
                .mecanicosResponsaveis(mecanicos)
                .status(StatusServico.AGUARDANDO_APROVACAO)
                .valorTotal(valorTotal)
                .dataInicio(dto.getDataInicio())
                .dataConclusao(dto.getDataConclusao())
                .build();

        servicoMecanicoAgendadoRepository.save(servico);
        return mapper.toDTO(servico);
    }
}
