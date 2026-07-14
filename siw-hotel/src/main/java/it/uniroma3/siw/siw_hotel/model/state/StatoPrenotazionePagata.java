package it.uniroma3.siw.siw_hotel.model.state;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * StatoPrenotazionePagata
 */
@Entity
@DiscriminatorValue("PAGATA")
public class StatoPrenotazionePagata extends StatoPrenotazione {



    public StatoPrenotazionePagata(Prenotazione prenotazione) {
        super(prenotazione);
    }

    public StatoPrenotazionePagata() {
        super();
    }

    @Override
    public String getNomeVisualizzato() {
        return "Pagata";
    }

    @Override
    public void terminaSoggiorno(Prenotazione p) {
        // Soggiorno finito, passiamo allo stato terminata
        p.setStato(new StatoPrenotazioneTerminata(p));
    }
}
