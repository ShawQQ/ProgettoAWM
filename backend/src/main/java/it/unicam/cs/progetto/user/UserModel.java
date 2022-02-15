package it.unicam.cs.progetto.user;

import it.unicam.cs.progetto.event.EventModel;
import it.unicam.cs.progetto.prenotation.PrenotationModel;
import it.unicam.cs.progetto.prenotation.dto.PrenotationDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserModel{

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private boolean isActive;
	private boolean isAdmin;
	@OneToMany
	private Set<PrenotationModel> prenotation;
	@ManyToMany
	private Set<EventModel> events;

	public UserModel(){}

	/**
	 * @param id id utente
	 * @param name nome utente
	 * @param surname cognome utente
	 * @param email email utente
	 * @param password password utente
	 * @param isActive stato account utente
	 * @param isAdmin account admin o no
	 * @param prenotation prenotazioni utente
	 * @param events eventi prenotati
	 */
	public UserModel(Long id, String name, String surname, String email, String password, boolean isActive, boolean isAdmin, Set<PrenotationModel> prenotation, Set<EventModel> events){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
		this.prenotation = prenotation;
		this.events = events;
	}

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<PrenotationModel> getPrenotation() {
		return prenotation;
	}

	public void setPrenotation(Set<PrenotationModel> prenotation) {
		this.prenotation = prenotation;
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

	public Set<EventModel> getEvents() {
		return events;
	}

	public void setEvents(Set<EventModel> events) {
		this.events = events;
	}

	public Set<PrenotationDto> getPrenotationDto() {
		Set<PrenotationDto> prenotations = new HashSet<>();
		this.getPrenotation()
				.stream()
				.forEach(p -> prenotations.add(new PrenotationDto(p.getId(), p.getStatus(), p.getDuration(), p.getTime(), p.getDate())));
		return prenotations;
	}
}
