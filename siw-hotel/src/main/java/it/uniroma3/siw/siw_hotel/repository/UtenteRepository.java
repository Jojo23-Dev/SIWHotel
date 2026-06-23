package it.uniroma3.siw.siw_hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.siw_hotel.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente,Long> {

}
