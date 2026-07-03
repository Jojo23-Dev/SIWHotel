package it.uniroma3.siw.siw_hotel.model;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
// in caso agggiungi @NamedQuery
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private double prezzo;

    @Column(nullable=false)
    private String tipo;

    @Column(nullable=false)
    private Long dimensione;

    @Column(length = 2000)
    private String descrizione;

    @Column(nullable=false)
    private Long letti;

    @Column(nullable=false)
    private Long persone;
    
    @OneToMany(mappedBy = "camera")
    private Collection<Prenotazione> prenotazioni;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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


    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Camera other = (Camera) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
