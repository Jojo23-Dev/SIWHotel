package it.uniroma3.siw.siw_hotel.model.state;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * StatoPrenotazioneTerminata
 */
@Entity
@DiscriminatorValue("TERMINATA")
public class StatoPrenotazioneTerminata extends StatoPrenotazione{

    public StatoPrenotazioneTerminata(Prenotazione prenotazione) {
        super(prenotazione);
    }
    protected StatoPrenotazioneTerminata( ) {
        super();
    }
    @Override
    public String getNomeVisualizzato() {
        return "Terminata";
    }

    @Override
    public boolean isCancellabile() {
        return false; // Impossibile cancellare il passato
    }
}
