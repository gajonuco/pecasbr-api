package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.PecaDAO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

@Component
public class PecaServiceImpl implements IPecaService {
    
    @Autowired
    private PecaDAO dao;

    @Override
    public Peca inserirNovaPeca(Peca peca) {
        // TODO Auto-generated method stub
       try {
            dao.save(peca);
            return peca;
       } catch (Exception ex) {
        System.out.println("---- PecaService.inserirNovaPeca()-----");
            ex.printStackTrace();
        System.out.println("-------------------------------------");
       }
       return null;
    }

    @Override
    public Peca alterarPeca(Peca peca) {
        // TODO Auto-generated method stub
        try {
            dao.save(peca);
            return peca;
       } catch (Exception ex) {
        System.out.println("---- PecaService.aletrarPeca()-----");
            ex.printStackTrace();
        System.out.println("-------------------------------------");
       }
       return null;
    }
    

    @Override
    public ArrayList<Peca> listarTodos() {
        // TODO Auto-generated method stub
        return (ArrayList<Peca>)dao.findAll();
    }

    @Override
    public ArrayList<Peca> listarDisponiveis() {
        // TODO Auto-generated method stub
       return dao.findAllByDisponivel(1); // Considero todas as peças '1' como disponíveis
    }

    @Override
    public ArrayList<Peca> listarPorCategoria(CategoriaPeca categoriaPeca) {
        // TODO Auto-generated method stub
        return dao.findAllByCategoriaPecaAndDisponivel( categoriaPeca,1);
    }

    @Override
    public ArrayList<Peca> listarIndisponiveis() {
        // TODO Auto-generated method stub
        return dao.findAllByDisponivel(0);
    }

    @Override
    public Peca recuperarPorId(int idPeca) {
        // TODO Auto-generated method stub
        return dao.findById(idPeca).orElse(null);
    }

    @Override
    public ArrayList<Peca> listarPecaPorPalavraChave(String palavraChave) {
        // TODO Auto-generated method stub
        return dao.findByNomeContainingOrDetalheContaining(palavraChave, palavraChave);
    }

    @Override
    public ArrayList<Peca> listarDestaques() {
        // TODO Auto-generated method stub
     return dao.findAllByDestaque(1);
    }

}
