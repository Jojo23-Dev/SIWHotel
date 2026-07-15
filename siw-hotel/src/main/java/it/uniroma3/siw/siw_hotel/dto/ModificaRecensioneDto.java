package it.uniroma3.siw.siw_hotel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class ModificaRecensioneDto {
    // attributi recensione
    @Min(value = 1, message = "Il voto minimo è 1 stella")
    @Max(value = 5, message = "Il voto massimo è 5 stelle")
    private int numeroStelle;

    @Size(max = 2000, message = "Il commento non può superare i 2000 caratteri")
    private String testo;

    public int getNumeroStelle() {
        return numeroStelle;
    }

    public void setNumeroStelle(int numeroStelle) {
        this.numeroStelle = numeroStelle;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    
}
