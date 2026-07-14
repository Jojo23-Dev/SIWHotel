package it.uniroma3.siw.siw_hotel.model.state;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_stato", discriminatorType = DiscriminatorType.STRING)
public abstract class StatoPrenotazione {
    @OneToOne( cascade = CascadeType.ALL)
    private Prenotazione prenotazione;

    protected StatoPrenotazione() {
        
    }

    public StatoPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Metodo che ogni stato deve implementare per mostrare il nome nell'HTML
    public abstract String getNomeVisualizzato();

    // Ogni stato deciderà autonomamente se può essere cancellato o no!
    public boolean isCancellabile() {
        if (this.prenotazione.getDataCheckIn() == null) {
            return false;
        }
        // Calcola i giorni di differenza tra OGGI e la data del Check-in
        long giorniMancanti = ChronoUnit.DAYS.between(LocalDate.now(), this.prenotazione.getDataCheckIn());
    
        // Ritorna true solo se mancano 3 o più giorni
        return giorniMancanti >= 3;
    }

    // Esempio di transizione di stato: il comportamento cambia in base a chi lo implementa
    public void confermaPagamento(Prenotazione prenotazione) {
        throw new IllegalStateException("Impossibile pagare in questo stato: " + getNomeVisualizzato());
    }

    public void terminaSoggiorno(Prenotazione prenotazione) {
        throw new IllegalStateException("Impossibile terminare il soggiorno in questo stato: " + getNomeVisualizzato());
    }

    // Getter e Setter per ID...
}
