package it.unicam.cs.progetto.prenotation.exception;

public class NoFreeSpotException extends Exception{
    public NoFreeSpotException(String message){
        super(message);
    }
}