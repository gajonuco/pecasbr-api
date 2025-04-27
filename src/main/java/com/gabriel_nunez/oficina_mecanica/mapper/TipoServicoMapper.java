package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.request.TipoServicoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.TipoServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.TipoServico;
import org.springframework.stereotype.Component;

@Component
public class TipoServicoMapper {

    public TipoServico toEntity(TipoServicoRequestDTO dto) {
        return TipoServico.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .valorBase(dto.getValorBase())
                .build();
    }

    public TipoServicoResponseDTO toDTO(TipoServico tipoServico) {
        TipoServicoResponseDTO dto = new TipoServicoResponseDTO();
        dto.setId(tipoServico.getId());
        dto.setNome(tipoServico.getNome());
        dto.setDescricao(tipoServico.getDescricao());
        dto.setValorBase(tipoServico.getValorBase());
        return dto;
    }
}
