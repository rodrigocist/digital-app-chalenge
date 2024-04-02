package com.digital.challenge.exception;

public class NewsException extends RuntimeException{
    public NewsException() {
        super();
    }
    public NewsException(String mensaje){
        super(mensaje);
    }

    public NewsException(String message, Throwable cause){
        super(message, cause);
    }


}
