package it.uniroma3.siw.siw_hotel.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.service.CameraService;

@Controller
public class CameraController {

    private CameraService cameraService;
    
    @GetMapping("/movies/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("movie", this.cameraService.findById(id));
    return "movies/show";
    }

    @GetMapping("/movies")
    public String list(Model model) {
        Iterable<Camera> allMovies = this.cameraService.findAll();
        model.addAttribute("movies", allMovies);
    return "movies/list";
    }

    
}
