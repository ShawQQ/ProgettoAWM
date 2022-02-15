package it.unicam.cs.progetto.prenotation;

import it.unicam.cs.progetto.prenotation.enums.PrenotationDuration;
import it.unicam.cs.progetto.prenotation.enums.PrenotationStatus;
import it.unicam.cs.progetto.prenotation.enums.PrenotationTime;
import it.unicam.cs.progetto.user.UserModel;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PrenotationModel {
	@Id
	@GeneratedValue
	private Long id;
	private PrenotationStatus status;
	private PrenotationDuration duration;
	private PrenotationTime time;
	private Date date;
	@ManyToOne
	private UserModel user;

	public PrenotationModel(){}

	public PrenotationModel(PrenotationStatus status, PrenotationDuration duration, UserModel user, PrenotationTime time, Date date) {
		this.status = status;
		this.duration = duration;
		this.user = user;
		this.time = time;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PrenotationStatus getStatus() {
		return status;
	}

	public void setStatus(PrenotationStatus status) {
		this.status = status;
	}

	public PrenotationDuration getDuration() {
		return duration;
	}

	public void setDuration(PrenotationDuration duration) {
		this.duration = duration;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public PrenotationTime getTime(){
		return this.time;
	}

	public void setTime(PrenotationTime time){
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
