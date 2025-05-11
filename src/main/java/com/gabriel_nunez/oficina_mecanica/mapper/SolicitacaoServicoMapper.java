package com.gabriel_nunez.oficina_mecanica.mapper;

import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dto.response.SolicitacaoServicoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;

@Component
public class SolicitacaoServicoMapper {
    public SolicitacaoServicoResponseDTO toDTO(SolicitacaoServico entity) {
        return SolicitacaoServicoResponseDTO.builder()
                .nomeCliente(entity.getCliente().getCliente().getNome()) // acesso aninhado
                .nomeServico(entity.getNomeServico())
                .dataHora(entity.getDataHora())
                .build();
    }
}
