/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dao.PecaDAO
 *  com.gabriel_nunez.oficina_mecanica.dao.PecaImagemDAO
 *  com.gabriel_nunez.oficina_mecanica.dto.PecaImagemDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.ReordenarImagensDTO
 *  com.gabriel_nunez.oficina_mecanica.model.Peca
 *  com.gabriel_nunez.oficina_mecanica.model.PecaImagem
 *  com.gabriel_nunez.oficina_mecanica.service.IUploadService
 *  com.gabriel_nunez.oficina_mecanica.service.PecaImagemService
 *  org.springframework.stereotype.Component
 *  org.springframework.transaction.annotation.Transactional
 */
package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.dao.PecaDAO;
import com.gabriel_nunez.oficina_mecanica.dao.PecaImagemDAO;
import com.gabriel_nunez.oficina_mecanica.dto.PecaImagemDTO;
import com.gabriel_nunez.oficina_mecanica.dto.ReordenarImagensDTO;
import com.gabriel_nunez.oficina_mecanica.model.Peca;
import com.gabriel_nunez.oficina_mecanica.model.PecaImagem;
import com.gabriel_nunez.oficina_mecanica.service.IUploadService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PecaImagemService {
    private static final int MAX_IMAGENS = 10;
    private final PecaImagemDAO pecaImagemDAO;
    private final PecaDAO pecaDAO;
    private final IUploadService uploadService;

    public PecaImagemService(PecaImagemDAO pecaImagemDAO, PecaDAO pecaDAO, IUploadService uploadService) {
        this.pecaImagemDAO = pecaImagemDAO;
        this.pecaDAO = pecaDAO;
        this.uploadService = uploadService;
    }

    public List<PecaImagemDTO> listarImagensPorPeca(Integer idPeca) {
        ArrayList<PecaImagemDTO> imagensDTO = new ArrayList<PecaImagemDTO>();
        this.pecaImagemDAO.findByPecaIdOrderByOrdemAsc(idPeca).forEach(imagem -> {
            PecaImagemDTO dto = new PecaImagemDTO();
            dto.setId(imagem.getId());
            dto.setLinkImagem(imagem.getLinkImagem());
            dto.setOrdem(imagem.getOrdem());
            dto.setPrincipal(imagem.getPrincipal());
            imagensDTO.add(dto);
        });
        return imagensDTO;
    }

    @Transactional
    public PecaImagemDTO adicionarImagem(Integer idPeca, String linkImagem, Integer principal) {
        if (this.pecaImagemDAO.countByPecaId(idPeca) >= 10L) {
            throw new RuntimeException("Limite de 10 imagens por pe\u00e7a atingido.");
        }
        Peca peca = (Peca)this.pecaDAO.findById(idPeca).orElseThrow(() -> new RuntimeException("Pe\u00e7a n\u00e3o encontrada."));
        if (principal != null && principal == 1) {
            this.pecaImagemDAO.findByPecaIdOrderByOrdemAsc(idPeca).forEach(img -> {
                if (img.getPrincipal() != null && img.getPrincipal() == 1) {
                    img.setPrincipal(Integer.valueOf(0));
                    this.pecaImagemDAO.save(img);
                }
            });
        }
        PecaImagem imagem = new PecaImagem();
        imagem.setLinkImagem(linkImagem);
        imagem.setPrincipal(Integer.valueOf(principal != null ? principal : 0));
        imagem.setPeca(peca);
        PecaImagem imagemSalva = (PecaImagem)this.pecaImagemDAO.save(imagem);
        PecaImagemDTO dto = new PecaImagemDTO();
        dto.setId(imagemSalva.getId());
        dto.setLinkImagem(imagemSalva.getLinkImagem());
        dto.setOrdem(imagemSalva.getOrdem());
        dto.setPrincipal(imagemSalva.getPrincipal());
        return dto;
    }

    @Transactional
    public void removerImagem(Integer idImagem) {
        this.pecaImagemDAO.deleteById(idImagem);
    }

    @Transactional
    public void definirPrincipal(Integer idPeca, Integer idImagem) throws Exception {
        Peca peca = (Peca)this.pecaDAO.findById(idPeca).orElseThrow(() -> new Exception("Pe\u00e7a n\u00e3o encontrada."));
        this.pecaImagemDAO.findByPecaIdOrderByOrdemAsc(idPeca).forEach(img -> {
            if (img.getPrincipal() != null && img.getPrincipal() == 1) {
                img.setPrincipal(Integer.valueOf(0));
                this.pecaImagemDAO.save(img);
            }
        });
        PecaImagem imagem = (PecaImagem)this.pecaImagemDAO.findById(idImagem).orElseThrow(() -> new Exception("Imagem n\u00e3o encontrada."));
        imagem.setPrincipal(Integer.valueOf(1));
        this.pecaImagemDAO.save(imagem);
    }

    @Transactional
    public void reordenarImagens(List<ReordenarImagensDTO> ordens) {
        ordens.forEach(dto -> this.pecaImagemDAO.findById(dto.getId()).ifPresent(img -> {
            img.setOrdem(dto.getOrdem());
            this.pecaImagemDAO.save(img);
        }));
    }
}

