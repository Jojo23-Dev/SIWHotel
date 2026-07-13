package it.uniroma3.siw.siw_hotel.model;

import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
// in caso agggiungi @NamedQuery
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable=false)
    private String nome,cognome;

    @Column(nullable=false)
    private LocalDate dataDiNascita;

    @Column(nullable=false)
    private String nazione;

    @Column(length = 40, nullable=false)
    private String telefono;
    
    @Column(length = 40, nullable=false, unique=true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable=false, unique=true)
    private Credenziali credenziali;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.REMOVE)
    private Collection<Prenotazione> prenotazioni;

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.REMOVE)
    private Collection<Recensione> recensioni;

    
    public Collection<Recensione> getRecensioni() {
        return recensioni;
    }
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }
    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
    public String getNazione() {
        return nazione;
    }
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Credenziali getCredenziali() {
        return credenziali;
    }
    public void setCredenziali(Credenziali credenziali) {
        this.credenziali = credenziali;
    }
    public Collection<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }
    public void aggiungiPrenotazione(Prenotazione prenotazione) {
        // Aggiunge la prenotazione alla lista di questo utente
        if (this.prenotazioni != null) {
            this.prenotazioni.add(prenotazione);
        }
    }
    public void rimuoviPrenotazione(Prenotazione prenotazione){
        if(this.prenotazioni != null){
            this.prenotazioni.remove(prenotazione);
        }
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
        Utente other = (Utente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
