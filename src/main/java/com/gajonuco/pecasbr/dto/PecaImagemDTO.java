/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.dto.PecaImagemDTO
 */
package com.gajonuco.pecasbr.dto;

public class PecaImagemDTO {
    private Integer id;
    private String linkImagem;
    private Integer ordem;
    private Integer principal;

    public PecaImagemDTO() {
    }

    public PecaImagemDTO(Integer id, String linkImagem, Integer ordem, Integer principal) {
        this.id = id;
        this.linkImagem = linkImagem;
        this.ordem = ordem;
        this.principal = principal;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLinkImagem() {
        return this.linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }
}

