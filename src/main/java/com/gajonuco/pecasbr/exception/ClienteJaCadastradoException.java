package com.gajonuco.pecasbr.exception;

public class ClienteJaCadastradoException extends RuntimeException {
    public ClienteJaCadastradoException(){
        super("Já existe uma conta cadastrada com esses dados.");
    }
}
