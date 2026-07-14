package it.uniroma3.siw.siw_hotel.model.state;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NON_PAGATA")
public class StatoPrenotazioneNonPagata extends StatoPrenotazione {

    public StatoPrenotazioneNonPagata(Prenotazione prenotazione) {
        super(prenotazione);
    }
    protected StatoPrenotazioneNonPagata( ) {
        super();
    }

    @Override
    public String getNomeVisualizzato() {
        return "Da Pagare";
    }


    @Override
    public void confermaPagamento(Prenotazione p) {
        // Logica di transizione: se pago, lo stato della prenotazione cambia!
        p.setStato(new StatoPrenotazionePagata(p));
    }
}
