package com.gabriel_nunez.oficina_mecanica.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

public interface PecaDAO extends CrudRepository <Peca, Integer>{

    public ArrayList<Peca> findAllByDisponivel(int disponivel);
    Optional<Peca> findById(int id);
    public ArrayList<Peca> findAllByCategoriaPecaAndDisponivel(CategoriaPeca categoriaPeca, int disponivel);
    public  ArrayList<Peca> findAllByCategoriaPeca(CategoriaPeca categoriaPeca);
    public ArrayList<Peca> findByNomeContainingOrDetalheContaining(String keyNome, String keyDetalhe);
}
