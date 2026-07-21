package it.uniroma3.siw.siw_hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.repository.UtenteRepository;

@Service
public class UtenteService {

    public static final int DIMENSIONE_PAGINA = 10;

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

	// Pagina 1-based: usata dall'anagrafica clienti (admin) al posto di getUtenti()
	public Page<Utente> getPrimi10Utenti(int pagina) {
		return this.utenteRepository.findPrimi10(PageRequest.of(pagina - 1, DIMENSIONE_PAGINA));
	}

	public Utente salvaUtente(Utente utente){
		return this.utenteRepository.save(utente);
	}


	// Il nuovo metodo che cerca direttamente tramite lo username
    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findByCredenzialiUsername(username);
    }

    // Cerca l'utente tramite email (usato dal login Google/OIDC)
    public Utente getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }


	public void eliminaUtente(Utente utente){
		this.utenteRepository.delete(utente);
	}

}
