package it.uniroma3.siw.siw_hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.siw_hotel.model.Credenziali;
import it.uniroma3.siw.siw_hotel.model.Utente;

public interface CredenzialiRepository extends JpaRepository<Credenziali,Long>{

    public Optional<Credenziali> findByUsername(String username);





}
