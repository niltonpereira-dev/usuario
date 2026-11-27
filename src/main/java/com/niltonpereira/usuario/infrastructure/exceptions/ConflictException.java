package com.niltonpereira.usuario.infrastructure.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
