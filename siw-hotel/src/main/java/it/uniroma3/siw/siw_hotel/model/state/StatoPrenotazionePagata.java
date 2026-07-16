package it.uniroma3.siw.siw_hotel.model.state;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.repository.PrenotazioneRepository;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * StatoPrenotazionePagata
 */
@Entity
@DiscriminatorValue("PAGATA")
public class StatoPrenotazionePagata extends StatoPrenotazione {


    @Override
    public String getNomeVisualizzato() {
        return "Pagata";
    }

    @Override
    public void terminaSoggiorno(Prenotazione p, PrenotazioneService ps) {
        // Soggiorno finito, passiamo allo stato terminata
        StatoPrenotazione statoIniziale = ps.getStatoPrenotazioneById(2L).get();
        p.setStato(statoIniziale);
    }
}
