package it.uniroma3.siw.siw_hotel.dto.admin;

import java.time.LocalDate;

import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.model.state.StatoPrenotazione;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class ModificaPrenotazioneDto {
     
    private LocalDate dataCheckIn;
    
    private LocalDate dataCheckOut;
    
    private String note;

    private double prezzoTotale;
    
    private Recensione recensione;

    private StatoPrenotazione stato;

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }

    public StatoPrenotazione getStato() {
        return stato;
    }

    public void setStato(StatoPrenotazione stato) {
        this.stato = stato;
    }

}
