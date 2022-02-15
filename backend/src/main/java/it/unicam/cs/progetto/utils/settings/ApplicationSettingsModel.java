package it.unicam.cs.progetto.utils.settings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ApplicationSettingsModel{
	@Id
	@GeneratedValue
	private Long id;
	private int maxPrenotation;

	public ApplicationSettingsModel(){}

	public ApplicationSettingsModel(int maxPrenotation) {
		this.maxPrenotation = maxPrenotation;
	}

	public int getMaxPrenotation() {
		return maxPrenotation;
	}

	public void setMaxPrenotation(int maxPrenotation) {
		this.maxPrenotation = maxPrenotation;
	}
}