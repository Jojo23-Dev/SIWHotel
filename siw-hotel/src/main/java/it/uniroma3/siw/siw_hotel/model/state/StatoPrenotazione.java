package it.uniroma3.siw.siw_hotel.model.state;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.repository.PrenotazioneRepository;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_stato", discriminatorType = DiscriminatorType.STRING)
public abstract class StatoPrenotazione {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Metodo che ogni stato deve implementare per mostrare il nome nell'HTML
    public abstract String getNomeVisualizzato();

    // Esempio di transizione di stato: il comportamento cambia in base a chi lo implementa
    public void confermaPagamento(Prenotazione prenotazione, PrenotazioneService ps) {
        throw new IllegalStateException("Impossibile pagare in questo stato: " + getNomeVisualizzato());
    }

    public void terminaSoggiorno(Prenotazione prenotazione, PrenotazioneService ps) {
        throw new IllegalStateException("Impossibile terminare il soggiorno in questo stato: " + getNomeVisualizzato());
    }
}
