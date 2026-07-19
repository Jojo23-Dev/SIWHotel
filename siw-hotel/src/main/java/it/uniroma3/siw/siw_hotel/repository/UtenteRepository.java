package it.uniroma3.siw.siw_hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.siw_hotel.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente,Long> {

    // Cerca l'Utente il cui campo "credenziali"
    // ha un campo "username" uguale alla stringa passata.
    Utente findByCredenzialiUsername(String username);

    Utente findUtenteByEmail(String email);

}
