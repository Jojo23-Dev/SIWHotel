package it.uniroma3.siw.siw_hotel.service;

import it.uniroma3.siw.siw_hotel.model.Credenziali;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.model.state.StatoPrenotazione;
import it.uniroma3.siw.siw_hotel.repository.PrenotazioneRepository;
import it.uniroma3.siw.siw_hotel.repository.StatoPrenotazioneRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    public static final int DIMENSIONE_PAGINA = 10;

    private PrenotazioneRepository prenotazioneRepository;
    private StatoPrenotazioneRepository statoPrenotazioneRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, StatoPrenotazioneRepository statoPrenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.statoPrenotazioneRepository = statoPrenotazioneRepository;
    }

    public int contaSovrapposizioni(Long cameraId, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        return this.prenotazioneRepository.countSovrapposizioni(cameraId, dataCheckIn, dataCheckOut);
    }

    public Prenotazione savePrenotazione(Prenotazione prenotazione) {
        return this.prenotazioneRepository.save(prenotazione);
    }

    public Optional<Prenotazione> getPrenotazione(Long id){
        return this.prenotazioneRepository.findById(id);
    }
    public List<Prenotazione> getPrenotazioni(Utente utente){
        return this.prenotazioneRepository.findByCliente(utente);
    }

    public List<Prenotazione> getPrenotazioniTutte(){
        return this.prenotazioneRepository.findAll();
    }

    // Pagina 1-based: usata dall'elenco admin al posto di getPrenotazioniTutte()
    public Page<Prenotazione> getUltimi10Prenotazioni(int pagina){
        return this.prenotazioneRepository.findUltimi10(PageRequest.of(pagina - 1, DIMENSIONE_PAGINA));
    }

    // Pagina 1-based: usata dallo storico prenotazioni del cliente al posto di getPrenotazioni(utente)
    public Page<Prenotazione> getUltimi10PrenotazioniDiUtente(Utente utente, int pagina){
        return this.prenotazioneRepository.findUltimi10DiUtente(utente, PageRequest.of(pagina - 1, DIMENSIONE_PAGINA));
    }

    public void cancellaPrenotazione(Prenotazione prenotazione) {
        this.prenotazioneRepository.delete(prenotazione);
    }

    public Optional<StatoPrenotazione> getStatoPrenotazioneById(Long id){
        return this.statoPrenotazioneRepository.findById(id);
    }
}
