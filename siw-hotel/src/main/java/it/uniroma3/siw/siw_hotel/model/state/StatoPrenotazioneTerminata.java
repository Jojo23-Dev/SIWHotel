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

    @Override
    public String getNomeVisualizzato() {
        return "Terminata";
    }

}
