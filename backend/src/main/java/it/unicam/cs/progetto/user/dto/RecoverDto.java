package it.unicam.cs.progetto.user.dto;

import com.sun.istack.NotNull;

/**
 * Dto rappresentante il recupero password di un utente
 */
public class RecoverDto {
	@NotNull
	private String email;
	@NotNull
	private String newPassword;

	public RecoverDto(String email, String newPassword) {
		this.email = email;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
