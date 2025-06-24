package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.request.TipoServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.TipoServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.TipoServicoMecanicoMapper;
import com.gabriel_nunez.oficina_mecanica.model.TipoServicoMecanico;
import com.gabriel_nunez.oficina_mecanica.repository.TipoServicoMecanicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoServicoMecanicoService {

    private final TipoServicoMecanicoRepository tipoServicoMecanicooRepository;
    private final TipoServicoMecanicoMapper tipoServicoMapper;

    public TipoServicoMecanicoResponseDTO criarTipoServico(TipoServicoMecanicoRequestDTO dto) {
        TipoServicoMecanico tipoServico = tipoServicoMapper.toEntity(dto);
        tipoServicoMecanicooRepository.save(tipoServico);
        return tipoServicoMapper.toDTO(tipoServico);
    }

    public List<TipoServicoMecanicoResponseDTO> listarTodos() {
        return tipoServicoMecanicooRepository.findAll()
                .stream()
                .map(tipoServicoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
