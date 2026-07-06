package it.uniroma3.siw.siw_hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.repository.UtenteRepository;

@Service
public class UtenteService {

    private UtenteRepository utenteRepository;

	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
		
	}
	
	public Optional<Utente> getUtente(Long id) {
		return this.utenteRepository.findById(id);
	}

	public Iterable<Utente> getUtenti() {
		return this.utenteRepository.findAll();
	}
	
	public Utente salvaUtente(Utente utente){
		return this.utenteRepository.save(utente);
	}

	// Il nuovo metodo che cerca direttamente tramite lo username
    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByCredenzialiUsername(username);
    }

}
