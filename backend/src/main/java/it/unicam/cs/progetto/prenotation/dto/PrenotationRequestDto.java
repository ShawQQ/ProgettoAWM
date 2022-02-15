package it.unicam.cs.progetto.prenotation.dto;

import com.sun.istack.NotNull;
import it.unicam.cs.progetto.prenotation.enums.PrenotationDuration;
import it.unicam.cs.progetto.prenotation.enums.PrenotationTime;

import java.util.Date;

/**
 * Dto rappresentante una prenotazione
 */
public class PrenotationRequestDto{
    @NotNull
    private Date prenotationDate;
    @NotNull
    private PrenotationDuration prenotationDuration;

    private PrenotationTime prenotationTime;

    public PrenotationRequestDto(Date prenotationDate, PrenotationDuration prenotationDuration, PrenotationTime time){
        this.prenotationDate = prenotationDate;
        this.prenotationDuration = prenotationDuration;
        this.prenotationTime = time;
    }

    public Date getPrenotationDate(){
        return this.prenotationDate;
    }

    public void setPrenotationDate(Date prenotationDate){
        this.prenotationDate = prenotationDate;
    }

    public PrenotationDuration getPrenotationDuration(){
        return this.prenotationDuration;
    }

    public void setPrenotationDuration(PrenotationDuration prenotationDuration){
        this.prenotationDuration = prenotationDuration;
    }

    public PrenotationTime getPrenotationTime(){
        return this.prenotationTime;
    }

    public void setPrenotationTime(PrenotationTime prenotationTime){
        this.prenotationTime = prenotationTime;
    }
}