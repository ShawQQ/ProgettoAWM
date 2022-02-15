package it.unicam.cs.progetto.user.dto;

/**
 * Dto rappresentante il token d'autenticazione utilizzato per gli utenti
 */
public class UserToken {
	private String jwt;

	public UserToken(){}

	public UserToken(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
