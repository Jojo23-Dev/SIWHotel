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

import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

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

    public void cancellaPrenotazione(Prenotazione prenotazione) {
        this.prenotazioneRepository.delete(prenotazione);
    }

    public Optional<StatoPrenotazione> getStatoPrenotazioneById(Long id){
        return this.statoPrenotazioneRepository.findById(id);
    }
}
