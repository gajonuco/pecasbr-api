package com.gabriel_nunez.oficina_mecanica.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dao.CategoriaPecaDAO;
import com.gabriel_nunez.oficina_mecanica.model.CategoriaPeca;

@Component
public class CategoriaPecaServiceImpl implements ICategoriaPecaService {

    @Autowired
    private CategoriaPecaDAO dao;

    @Override
    public CategoriaPeca adicionarNovaCategoriaPeca(CategoriaPeca categoriaPeca) {
        try {
            if(categoriaPeca.getNome() != null && categoriaPeca.getNome().trim().length() > 0){
                dao.save(categoriaPeca);
                return categoriaPeca;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("DEBUG =" + ex.getMessage());
        } catch (Exception ex){
            System.out.println("DEBUG = " + ex.getMessage() );
        }

        return null;
    }
    


    @Override
    public CategoriaPeca alterarCategoriaPeca(CategoriaPeca categoriaPeca) {
        // TODO Auto-generated method stub
        try {
            if(categoriaPeca.getId() != null && categoriaPeca.getNome().trim().length() > 0){
                return dao.save(categoriaPeca);
            }
        } catch (Exception ex) {
            System.out.println("DEBUG = " + ex);
        }

        return null;
    }

    @Override
    public ArrayList<CategoriaPeca> recuperarTodasCategoriasPecas() {
        return (ArrayList<CategoriaPeca>)dao.findAll();
    }

    @Override
    public ArrayList<CategoriaPeca> recuperarPorPalavraChave(String palavraChave) {
        if(palavraChave != null){
            dao.findByNomeContaining(palavraChave);
        }
        return null;
    }



    @Override
    public CategoriaPeca recuperaPorID(int id) {
        // TODO Auto-generated method stub
        return dao.findById(id).orElse(null);
    }



    @Override
    public ArrayList<CategoriaPeca> recuperarTodasPeloId() {
        // TODO Auto-generated method stub
        return dao.findAllByOrderById();
    }

    
}