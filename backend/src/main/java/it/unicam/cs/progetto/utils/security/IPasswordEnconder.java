package it.unicam.cs.progetto.utils.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Interfaccia di utilit&agrave; per il trattamento delle password
 * @param <T> Tipo di encoder da utilizzare
 */
public interface IPasswordEnconder<T> {
	T getEncoder();
	String encodeString(String rawString);
	boolean matchPassword(String password, String hash);
}