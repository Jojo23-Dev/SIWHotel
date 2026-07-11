package it.uniroma3.siw.siw_hotel.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_hotel.model.Credenziali;
import it.uniroma3.siw.siw_hotel.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
    private CredenzialiRepository credenzialiRepository;
    public CredenzialiService(CredenzialiRepository credenzialiRepository) {
		this.credenzialiRepository = credenzialiRepository;
	}
    public Credenziali getCredenziali(Long id){
        return this.credenzialiRepository.findById(id).get();
    }
    public Credenziali getCredenziali(String username){
        return this.credenzialiRepository.findByUsername(username).get();
    }
    public Credenziali saveCredenziali(Credenziali credenziali){
        return this.credenzialiRepository.save(credenziali);
    }
    
    
}
