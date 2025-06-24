/*package com.gabriel_nunez.oficina_mecanica.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dto.request.OfertaSelecionadaRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.OfertaSelecionadaResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.ClienteUsuario;
import com.gabriel_nunez.oficina_mecanica.model.OfertaSelecionada;
import com.gabriel_nunez.oficina_mecanica.model.OfertaServico;

@Component
public class OfertaSelecionadaMapper {

    public OfertaSelecionada toEntity(OfertaSelecionadaRequestDTO dto, 
                                      OfertaServico ofertaOriginal,
                                      ClienteUsuario clienteUsuario) {
        return OfertaSelecionada.builder()
            .ofertaOriginal(ofertaOriginal)
            .preco(dto.getPreco())
            .descricao(dto.getDescricao())
            .clienteUsuario(clienteUsuario)
            .dataHoraSelecionada(LocalDateTime.now())
            .build();
    }

    public OfertaSelecionadaResponseDTO toDTO(OfertaSelecionada entity) {
        return OfertaSelecionadaResponseDTO.builder()
            .id(entity.getId())
            .nomeCliente(entity.getClienteUsuario().getCliente().getNome())
            .nomeTipoServico(entity.getOfertaOriginal().getTipoServico().getNome())
            .descricao(entity.getDescricao())
            .preco(entity.getPreco())
            .dataHoraSelecionada(entity.getDataHoraSelecionada())
            .build();
    }
}
*/

