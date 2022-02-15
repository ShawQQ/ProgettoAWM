package it.unicam.cs.progetto.user.dto;

import com.sun.istack.NotNull;

/**
 * Dto rappresentante la registrazione di un utente
 */
public class RegistrationDto {
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String confirmPassword;
	private String name;
	private String surname;

	public RegistrationDto(String email, String password, String confirmPassword, String name, String surname) {
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.name = name;
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
