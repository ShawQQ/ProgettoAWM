package it.unicam.cs.progetto.user.dto;

import it.unicam.cs.progetto.prenotation.dto.PrenotationDto;

import java.util.Set;

/**
 * Dto rappresentante un generico utente
 */
public class UserDto {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private boolean isActive;
	private boolean isAdmin;
	private Set<PrenotationDto> prenotations;

	public UserDto(Long id, String name, String surname, String email, boolean isActive, boolean isAdmin, Set<PrenotationDto> prenotationDtoSet) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
		this.prenotations = prenotationDtoSet;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public Set<PrenotationDto> getPrenotations() {
		return prenotations;
	}

	public void setPrenotations(Set<PrenotationDto> prenotations) {
		this.prenotations = prenotations;
	}
}
