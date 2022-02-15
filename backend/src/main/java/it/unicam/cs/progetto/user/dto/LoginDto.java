package it.unicam.cs.progetto.user.dto;

import com.sun.istack.NotNull;

/**
 * Dto rappresentante login di un utente
 */
public class LoginDto {
	@NotNull
	private String email;
	@NotNull
	private String password;

	public LoginDto(String email, String password) {
		this.email = email;
		this.password = password;
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

	public void setPassword(String password) {
		this.password = password;
	}
}
