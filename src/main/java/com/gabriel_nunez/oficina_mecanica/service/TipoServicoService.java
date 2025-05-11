package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dto.request.TipoServicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.TipoServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.TipoServicoMapper;
import com.gabriel_nunez.oficina_mecanica.model.TipoServico;
import com.gabriel_nunez.oficina_mecanica.repository.TipoServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoServicoService {

    private final TipoServicoRepository tipoServicoRepository;
    private final TipoServicoMapper tipoServicoMapper;

    public TipoServicoResponseDTO criarTipoServico(TipoServicoRequestDTO dto) {
        TipoServico tipoServico = tipoServicoMapper.toEntity(dto);
        tipoServicoRepository.save(tipoServico);
        return tipoServicoMapper.toDTO(tipoServico);
    }

    public List<TipoServicoResponseDTO> listarTodos() {
        return tipoServicoRepository.findAll()
                .stream()
                .map(tipoServicoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
