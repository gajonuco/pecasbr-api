package com.gajonuco.pecasbr.dto;

public record PecaVariacaoConsultaDTO(
        String cor,
        String hexCode,
        String tamanho,
        boolean disponivel
) {
    public static PecaVariacaoConsultaDTO from(PecaVariacaoDTO dto) {
        boolean disponivel = dto.getQuantidadeEstoque() != null && dto.getQuantidadeEstoque() > 0;
        return new PecaVariacaoConsultaDTO(dto.getCor(), dto.getHexCode(), dto.getTamanho(), disponivel);
    }
}
