package it.uniroma3.siw.siw_hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import it.uniroma3.siw.siw_hotel.service.UtenteService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/dashboard")
    public String mostraDashboard() {
        return "admin/dashboard";
    }

    // @GetMapping("/prenotazioni")
    // public String elencoPrenotazioni(Model model) {
    //     model.addAttribute("prenotazioni", this.prenotazioneService.getPrenotazioniTutte()); 
    //     return "admin/prenotazioni";
    // }

    @GetMapping("/clienti")
    public String elencoClienti(Model model) {
        model.addAttribute("clienti", this.utenteService.getUtenti()); 
        return "admin/clienti";
    }

}
