package main.ejercicio3.exceptions;

public class EventoException extends RuntimeException {

    public EventoException(String mensajeError){
        super(mensajeError);
    }
}
