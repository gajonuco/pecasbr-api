package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.request.PecaRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.PecaResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import org.springframework.stereotype.Component;

@Component
public class PecaMapper {

    public Peca toEntity(PecaRequestDTO dto) {
        Peca peca = new Peca();
        peca.setNome(dto.getNome());
        peca.setSku(dto.getSku().trim().toUpperCase());
        peca.setMarca(dto.getMarca());
        peca.setPreco(dto.getPreco());
        peca.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
        peca.setDataVencimento(dto.getDataVencimento());
        return peca;
    }

    public PecaResponseDTO toResponse(Peca peca) {
        return PecaResponseDTO.builder()
            .id(peca.getId())
            .nome(peca.getNome())
            .sku(peca.getSku())
            .marca(peca.getMarca())
            .preco(peca.getPreco())
            .quantidadeEmEstoque(peca.getQuantidadeEmEstoque())
            .dataVencimento(peca.getDataVencimento())
            .obsoleta(peca.isObsoleta())
            .build();
    }
}
