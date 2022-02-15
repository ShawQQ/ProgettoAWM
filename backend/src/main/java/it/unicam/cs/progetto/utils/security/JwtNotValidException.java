package it.unicam.cs.progetto.utils.security;

public class JwtNotValidException extends Exception {
	public JwtNotValidException(String message) {
		super(message);
	}
}
