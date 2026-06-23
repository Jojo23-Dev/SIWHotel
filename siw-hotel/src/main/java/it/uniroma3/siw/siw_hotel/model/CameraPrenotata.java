package it.uniroma3.siw.siw_hotel.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
// in caso agggiungi @NamedQuery
public class CameraPrenotata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPrenotazione;

    @Column(nullable=false)
    private Date dataCheckIn;

    @Column(nullable=false)
    private Date dataCheckOut;

    @Column(length = 2000)
    private String note;
    

    @ManyToOne
    @Column(nullable=false)
    private Utente cliente;

    @ManyToOne
    @Column(nullable=false)
    private Camera camera;
   
   
   
    public Long getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(Long idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public Date getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(Date dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Date getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(Date dataCheckOut) {
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
        CameraPrenotata other = (CameraPrenotata) obj;
        if (idPrenotazione == null) {
            if (other.idPrenotazione != null)
                return false;
        } else if (!idPrenotazione.equals(other.idPrenotazione))
            return false;
        return true;
    }
}
