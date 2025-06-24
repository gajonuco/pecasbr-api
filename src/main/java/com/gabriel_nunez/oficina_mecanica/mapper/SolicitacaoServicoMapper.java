/*package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.response.SolicitacaoServicoResponseDTO;
//import com.gabriel_nunez.oficina_mecanica.model.SolicitacaoServico;
import org.springframework.stereotype.Component;

@Component
public class SolicitacaoServicoMapper {
    public SolicitacaoServicoResponseDTO toDTO(SolicitacaoServico entity) {
        return SolicitacaoServicoResponseDTO.builder()
                .id(entity.getId())
                .nomeCliente(entity.getCliente().getCliente().getNome())
                .loginCliente(entity.getCliente().getLogin()) // <-- Adicionado
                .nomeServico(entity.getNomeServico())
                .dataHora(entity.getDataHora())
                .tipoServicoId(entity.getTipoServico().getId())
                .clienteId(entity.getCliente().getId())
                .build();
    }
}
*/