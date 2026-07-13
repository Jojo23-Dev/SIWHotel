package it.uniroma3.siw.siw_hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.service.RecensioneService;

@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @GetMapping("/recensioni")
    public String mostraRecensioni(Model model) {
        // Recuperiamo tutte le recensioni dal DB usando il Service che hai già creato
        List<Recensione> listaRecensioni = this.recensioneService.getRecensioniTutte();
        
        // Passiamo la lista alla pagina HTML
        model.addAttribute("recensioni", listaRecensioni);
        
        return "recensioni"; // Cerca il file recensioni.html
    }
}
