package it.uniroma3.siw.siw_hotel.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.service.CameraService;

@Controller
public class HomeController {
    

	@Autowired
	private CameraService cameraService;

	@GetMapping("/")
	public String getHome(Model model) {
		Iterable<Camera> lista = this.cameraService.getCamere();

		model.addAttribute("camere",lista);
		return "home.html";
	}

}
