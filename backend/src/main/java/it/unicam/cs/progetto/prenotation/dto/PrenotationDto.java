package it.unicam.cs.progetto.prenotation.dto;

import it.unicam.cs.progetto.prenotation.enums.PrenotationDuration;
import it.unicam.cs.progetto.prenotation.enums.PrenotationStatus;
import it.unicam.cs.progetto.prenotation.enums.PrenotationTime;

import java.util.Date;

public class PrenotationDto {
	private Long id;
	private PrenotationStatus status;
	private PrenotationDuration duration;
	private PrenotationTime time;
	private Date date;

	public PrenotationDto(Long id, PrenotationStatus status, PrenotationDuration duration, PrenotationTime time, Date date) {
		this.id = id;
		this.status = status;
		this.duration = duration;
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

	public PrenotationTime getTime() {
		return time;
	}

	public void setTime(PrenotationTime time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
