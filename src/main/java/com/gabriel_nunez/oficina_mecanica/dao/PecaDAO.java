package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

public interface PecaDAO extends CrudRepository <Peca, Integer>{

    public Page<Peca> findAllByDisponivel(int disponivel, Pageable pageable);
    Optional<Peca> findById(int id);
    public ArrayList<Peca> findAllByCategoriaPecaAndDisponivel(CategoriaPeca categoriaPeca, int disponivel);
    public  ArrayList<Peca> findAllByCategoriaPeca(CategoriaPeca categoriaPeca);
    public ArrayList<Peca> findByNomeContainingOrDetalheContaining(String keyNome, String keyDetalhe);
	public Page<Peca> findAllByDestaqueAndDisponivel(int destaque, int disponivel, Pageable pageable);
	public Page<Peca> findAllByDisponivelAndNomeContainingOrDisponivelAndDetalheContaining(int disponivel, String keyNome, int disponivel2, String keyDetalhe, Pageable pageable);
	

}



