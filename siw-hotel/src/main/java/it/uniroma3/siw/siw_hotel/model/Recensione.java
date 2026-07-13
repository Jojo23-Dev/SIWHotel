package it.uniroma3.siw.siw_hotel.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;

@Entity
public class Recensione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 2000)
    private String testo;

    private LocalDateTime dataEOra;

    
    private int numeroStelle;

    @OneToOne
    private Prenotazione prenotazione;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente cliente;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTesto() {
        return testo;
    }
    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }
    public void setTesto(String testo) {
        this.testo = testo;
    }


    public LocalDateTime getDataEOra() {
        return dataEOra;
    }


    public void setDataEOra(LocalDateTime dataEOra) {
        this.dataEOra = dataEOra;
    }


    public int getNumeroStelle() {
        return numeroStelle;
    }


    public void setNumeroStelle(int numeroStelle) {
        this.numeroStelle = numeroStelle;
    }


    public Utente getCliente() {
        return cliente;
    }


    public void setCliente(Utente cliente) {
        this.cliente = cliente;
    }


}
