package it.uniroma3.siw.siw_hotel.service;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.repository.PrenotazioneRepository;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    private PrenotazioneRepository prenotazioneRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public int contaSovrapposizioni(Long cameraId, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        return this.prenotazioneRepository.countSovrapposizioni(cameraId, dataCheckIn, dataCheckOut);
    }

    public Prenotazione savePrenotazione(Prenotazione prenotazione) {
        return this.prenotazioneRepository.save(prenotazione);
    }

}
