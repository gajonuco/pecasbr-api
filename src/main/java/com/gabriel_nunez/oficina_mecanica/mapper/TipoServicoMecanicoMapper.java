package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.request.TipoServicoMecanicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.TipoServicoMecanicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.TipoServicoMecanico;
import com.gabriel_nunez.oficina_mecanica.model.TipoServicoMecanico;

import org.springframework.stereotype.Component;

@Component
public class TipoServicoMecanicoMapper {

    public TipoServicoMecanico toEntity(TipoServicoMecanicoRequestDTO dto) {
        return TipoServicoMecanico.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .valorBase(dto.getValorBase())
                .build();
    }

    public TipoServicoMecanicoResponseDTO toDTO(TipoServicoMecanico tipoServico) {
        TipoServicoMecanicoResponseDTO dto = new TipoServicoMecanicoResponseDTO();
        dto.setId(tipoServico.getId());
        dto.setNome(tipoServico.getNome());
        dto.setDescricao(tipoServico.getDescricao());
        dto.setValorBase(tipoServico.getValorBase());
        return dto;
    }
}
