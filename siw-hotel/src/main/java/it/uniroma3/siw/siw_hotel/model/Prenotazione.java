package it.uniroma3.siw.siw_hotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.bind.Name;

import it.uniroma3.siw.siw_hotel.model.state.StatoPrenotazione;
import it.uniroma3.siw.siw_hotel.model.state.StatoPrenotazioneNonPagata;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
// in caso agggiungi @NamedQuery
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenotazione")
    private Long idPrenotazione;

    @Column(nullable=false)
    private LocalDate dataCheckIn;

    @Column(nullable=false)
    private LocalDate dataCheckOut;

    @Column(length = 2000)
    private String note;

    @Column(nullable=false)
    private double prezzoTotale;
    
    @OneToOne(mappedBy = "prenotazione", cascade = CascadeType.ALL)
    @JoinColumn(nullable = true)
    private Recensione recensione;

    @ManyToOne // gestisci ( cascade = CascadeType.ALL)
    @JoinColumn(name = "stato_id")
    private StatoPrenotazione stato;


    public void paga(PrenotazioneService ps) {
        this.stato.confermaPagamento(this, ps); // Passa se stessa allo stato
    }

    public void termina(PrenotazioneService ps) {
        this.stato.terminaSoggiorno(this, ps);
    }

    public StatoPrenotazione getStato() {
        return stato;
    }

    public void setStato(StatoPrenotazione stato) {
        this.stato = stato;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    @ManyToOne
    @JoinColumn(nullable=false)
    private Utente cliente;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Camera camera;
   
   
   public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }

    public Long getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(Long idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

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

    public Utente getCliente() {
        return cliente;
    }

    public void setCliente(Utente cliente) {
        this.cliente = cliente;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }



      // Ogni stato deciderà autonomamente se può essere cancellato o no!
    public boolean isCancellabile() {
        if ( !(this.getStato().getNomeVisualizzato().equals("Terminata")) ) {
            if (this.getDataCheckIn() == null) {
                return false;
            }
            // Calcola i giorni di differenza tra OGGI e la data del Check-in
            long giorniMancanti = ChronoUnit.DAYS.between(LocalDate.now(), this.getDataCheckIn());
        
            // Ritorna true solo se mancano 3 o più giorni
            return giorniMancanti >= 3;
        }
        else return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPrenotazione == null) ? 0 : idPrenotazione.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Prenotazione other = (Prenotazione) obj;
        if (idPrenotazione == null) {
            if (other.idPrenotazione != null)
                return false;
        } else if (!idPrenotazione.equals(other.idPrenotazione))
            return false;
        return true;
    }

}
