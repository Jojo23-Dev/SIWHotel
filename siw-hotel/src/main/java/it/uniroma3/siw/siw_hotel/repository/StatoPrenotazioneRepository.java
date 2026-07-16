package it.uniroma3.siw.siw_hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.state.StatoPrenotazione;

public interface StatoPrenotazioneRepository extends JpaRepository<StatoPrenotazione,Long> {

}
