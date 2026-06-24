package it.uniroma3.siw.siw_hotel.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.service.CameraService;

@Controller
public class CameraController {

    @Autowired
    private CameraService cameraService;
    
    @GetMapping("/camere/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        // 1. Recuperi l'Optional dal service
        Optional<Camera> cameraOptional = cameraService.getCamera(id);
    
    // 2. CONTROLLA se esiste! 
    if (cameraOptional.isPresent()) {
        // 3. Estratto l'oggetto reale (Camera)
        model.addAttribute("camera", cameraOptional.get());
        return "camera"; // nome del tuo file HTML
    } else {
        // Gestisci il caso in cui l'id non esiste (es. redirect a errore o elenco)
        return "redirect:/camere"; 
    }
    }

    @GetMapping("/camere")
    public String list(Model model) {
        Iterable<Camera> allCamere = this.cameraService.getCamere();
        model.addAttribute("camere", allCamere);
    return "camere";
    }

    
}
