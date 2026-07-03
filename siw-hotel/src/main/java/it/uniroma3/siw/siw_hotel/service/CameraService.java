package it.uniroma3.siw.siw_hotel.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.repository.CameraRepository;

@Service
public class CameraService {

    private CameraRepository cameraRepository;

	public CameraService(CameraRepository cameraRepository) {
		this.cameraRepository = cameraRepository;
	}


	public Optional<Camera> getCamera(Long id) {
		return this.cameraRepository.findById(id);
	}

	public Iterable<Camera> getCamere() {
		return this.cameraRepository.findAll();
	}

	

}
