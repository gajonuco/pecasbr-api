package com.gajonuco.pecasbr.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;

public interface PecaImagemDAO  extends CrudRepository<com.gajonuco.pecasbr.model.PecaImagem, Integer> {
    
    @Transactional
    @Query ("DELETE FROM PecaImagem pi WHERE pi.peca.id = :idPeca")
    @Modifying
    void deleteByPecaId(Integer idPeca);

    @Query("SELECT COUNT(pi) FROM PecaImagem pi WHERE pi.peca.id = :idPeca")
    long countByPecaId(Integer idPeca);

    public java.util.List<com.gajonuco.pecasbr.model.PecaImagem> findByPecaIdOrderByOrdemAsc(Integer idPeca);
}
