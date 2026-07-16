package it.uniroma3.siw.siw_hotel.dto.admin;

import jakarta.persistence.Column;

public class AggiungiCameraDto {
    
    private double prezzo;

    private String tipo;

    private Long dimensione;

    private String descrizione;

    private Long letti;

    private Long persone;

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getDimensione() {
        return dimensione;
    }

    public void setDimensione(Long dimensione) {
        this.dimensione = dimensione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getLetti() {
        return letti;
    }

    public void setLetti(Long letti) {
        this.letti = letti;
    }

    public Long getPersone() {
        return persone;
    }

    public void setPersone(Long persone) {
        this.persone = persone;
    }
}
