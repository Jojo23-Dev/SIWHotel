package it.uniroma3.siw.siw_hotel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.model.Utente;

public interface RecensioneRepository extends  JpaRepository<Recensione,Long>{

    public List<Recensione> findByCliente(Utente cliente);

    public List<Recensione> findByNumeroStelle(int numeroStelle);

    public Optional<Recensione> findByPrenotazione(Prenotazione prenotazione);

    // Usata dall'elenco pubblico delle recensioni al posto di findAll(): restituisce solo la "pagina" richiesta (10 elementi)
    @Query("SELECT r FROM Recensione r ORDER BY r.dataEOra DESC")
    Page<Recensione> findUltimi10(Pageable pageable);

}
