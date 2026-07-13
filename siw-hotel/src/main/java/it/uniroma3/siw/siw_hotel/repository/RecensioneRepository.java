package it.uniroma3.siw.siw_hotel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.model.Utente;

public interface RecensioneRepository extends  JpaRepository<Recensione,Long>{

    public List<Recensione> findByCliente(Utente cliente);

    public List<Recensione> findByNumeroStelle(int numeroStelle);

    public Optional<Recensione> findByPrenotazione(Prenotazione prenotazione);

}
