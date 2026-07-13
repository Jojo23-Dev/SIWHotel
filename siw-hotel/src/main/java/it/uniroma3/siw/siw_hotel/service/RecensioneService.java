package it.uniroma3.siw.siw_hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.repository.RecensioneRepository;

@Service
public class RecensioneService {
    private RecensioneRepository recensioneRepository;

    public RecensioneService(RecensioneRepository recensioneRepository) {
        this.recensioneRepository = recensioneRepository;
    }

    public Recensione getRecensione(Long id){
        return this.recensioneRepository.findById(id).get();
    }
    public Recensione getRecensioneDiPrenotazione(Prenotazione prenotazione){
        return this.recensioneRepository.findByPrenotazione(prenotazione).get();
    }
    public List<Recensione> getRecensioniDiCliente(Utente cliente){
        return this.recensioneRepository.findByCliente(cliente);
    }
    public List<Recensione> getRecensioniTutte(){
        return this.recensioneRepository.findAll();
    }
    public List<Recensione> getRecensioniByNumeroStelle(int numeroStelle){
        return this.recensioneRepository.findByNumeroStelle(numeroStelle);
    }


    public void eliminaRecensione(Long id){
        this.recensioneRepository.deleteById(id);
    }
    
    public Recensione saveCredenziali(Recensione recensione){
        return this.recensioneRepository.save(recensione);
    }

    
}
