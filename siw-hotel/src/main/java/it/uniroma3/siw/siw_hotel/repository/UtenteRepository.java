package it.uniroma3.siw.siw_hotel.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.siw_hotel.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente,Long> {

    // Cerca l'Utente il cui campo "credenziali"
    // ha un campo "username" uguale alla stringa passata.
    Utente findByCredenzialiUsername(String username);

    // Cerca l'Utente tramite email (usato dal login Google/OIDC)
    Utente findByEmail(String email);

    // Usata dall'anagrafica clienti (admin) al posto di findAll(): restituisce solo la "pagina" richiesta (10 elementi)
    @Query("SELECT u FROM Utente u ORDER BY u.id ASC")
    Page<Utente> findPrimi10(Pageable pageable);

}
