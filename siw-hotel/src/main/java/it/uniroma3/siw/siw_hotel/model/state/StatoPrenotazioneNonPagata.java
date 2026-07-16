package it.uniroma3.siw.siw_hotel.model.state;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.repository.PrenotazioneRepository;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NON_PAGATA")
public class StatoPrenotazioneNonPagata extends StatoPrenotazione {


    @Override
    public String getNomeVisualizzato() {
        return "Da Pagare";
    }


    @Override
    public void confermaPagamento(Prenotazione p, PrenotazioneService ps) {
        // Logica di transizione: se pago, lo stato della prenotazione cambia!
        StatoPrenotazione statoIniziale = ps.getStatoPrenotazioneById(1L).get();
        p.setStato(statoIniziale);
    }
}
