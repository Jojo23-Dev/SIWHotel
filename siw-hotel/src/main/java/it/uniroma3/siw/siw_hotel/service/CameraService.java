package it.uniroma3.siw.siw_hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.repository.CameraRepository;

@Service
public class CameraService {

    private CameraRepository cameraRepository;
	private Camera camera;


	@Autowired
	private PrenotazioneService prenotazioneService;

	public CameraService(CameraRepository cameraRepository) {
		this.cameraRepository = cameraRepository;
	}


	public Optional<Camera> getCamera(Long id) {
		return this.cameraRepository.findById(id);
	}

	public Iterable<Camera> getCamere() {
		return this.cameraRepository.findAll();
	}


    public Camera saveCamera(Camera camera) {
        return this.cameraRepository.save(camera);
        
	}

	public void eliminaCamera(Camera camera) {
        this.cameraRepository.delete(camera);
        
	}

	// TODO da vedere
    // public List<Camera> getCamereDisponibili(LocalDate checkIn, LocalDate checkOut) {
    //     // TODO Auto-generated method stub
	// 	Iterable<Camera> allCamere = this.cameraRepository.findAll();

	// 	List<Camera> allCamereDisponibili = new ArrayList<>();

	// 	if(checkIn == null || checkOut ==null){
	// 		allCamereDisponibili.addAll((List<Camera>)allCamere) ;
	// 		return allCamereDisponibili;
	// 	}


	// 	for(Camera c : allCamere){
	// 		 int sovrapposizioni = this.prenotazioneService.contaSovrapposizioni(c.getId(), checkIn, checkOut);

	// 		if(sovrapposizioni == 0){
	// 			allCamereDisponibili.add(c);
	// 		}
			
	// 	}
	// 	return allCamereDisponibili;
	// }

	
	

}
