package com.gajonuco.pecasbr.exception;

public class EnderecoNaoEncontradoException extends RuntimeException{
    public EnderecoNaoEncontradoException(){
        super("Endereço não encontrado.");
    }
}
