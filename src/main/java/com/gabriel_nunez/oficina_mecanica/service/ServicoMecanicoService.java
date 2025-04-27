package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.request.ServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.ServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.enums.StatusServico;
import com.gabriel_nunez.oficina_mecanica.mapper.ServicoMecanicoMapper;
import com.gabriel_nunez.oficina_mecanica.model.*;
import com.gabriel_nunez.oficina_mecanica.repository.*;
import com.gabriel_nunez.oficina_mecanica.user.User;
import com.gabriel_nunez.oficina_mecanica.user.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicoMecanicoService {

    private final ServicoMecanicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final PecaRepository pecaRepository;
    private final UserRepository userRepository;
    private final TipoServicoRepository tipoServicoRepository;
    private final ServicoMecanicoMapper mapper;

    public ServicoMecanicoService(ServicoMecanicoRepository servicoRepository,
                                  ClienteRepository clienteRepository,
                                  VeiculoRepository veiculoRepository,
                                  PecaRepository pecaRepository,
                                  UserRepository userRepository,
                                  TipoServicoRepository tipoServicoRepository,
                                  ServicoMecanicoMapper mapper) {
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.pecaRepository = pecaRepository;
        this.userRepository = userRepository;
        this.tipoServicoRepository = tipoServicoRepository;
        this.mapper = mapper;
    }

    public ServicoMecanicoResponseDTO criarServico(ServicoMecanicoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));

        TipoServico tipoServico = tipoServicoRepository.findById(dto.getTipoServicoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de serviço não encontrado"));

        List<Peca> pecas = pecaRepository.findAllById(dto.getPecasUtilizadasIds());
        List<User> mecanicos = userRepository.findAllById(dto.getMecanicosResponsaveisIds()).stream()
                .filter(user -> user.getRole() == UserRole.MECANICO)
                .toList();

        BigDecimal valorTotalPecas = pecas.stream()
                .map(Peca::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorTotal = tipoServico.getValorBase().add(valorTotalPecas);

        ServicoMecanico servico = ServicoMecanico.builder()
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

        servicoRepository.save(servico);
        return mapper.toDTO(servico);
    }
}
