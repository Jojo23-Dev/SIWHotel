package it.uniroma3.siw.siw_hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import jakarta.transaction.Transactional;


@Controller
public class UtenteController {

    // @Autowired
    // private GlobalController globalController;

    // @GetMapping("/area-personale")
    // public String apriAreaPersonale(Model model) {
    //     // UserDetails utenteCorrente =  this.globalController.getUtente();
    //     UserDetails utenteCorrente = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     model.addAttribute("utente", utenteCorrente);
    // return "area_personale";
    // }
    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private GlobalController globalController;

    @GetMapping("/area-personale")
    public String apriAreaPersonale(Model model) {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        Utente utenteCorrente = this.globalController.getUtente();
        model.addAttribute("utente", utenteCorrente);
        model.addAttribute("credenziali", utenteCorrente.getCredenziali());

        // 2. IL FIX: Passiamo la lista delle prenotazioni al Model!
        List<Prenotazione> miePrenotazioni = this.prenotazioneService.getPrenotazioni(utenteCorrente);
        
        if(!miePrenotazioni.isEmpty() || miePrenotazioni!=null){
            model.addAttribute("prenotazioni", miePrenotazioni);
        }
        return "utente/area_personale";
    }

    @GetMapping("/area-personale/profilo")
    public String apriDettagliProfilo(Model model) {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        Utente utenteCorrente = this.globalController.getUtente();
        
        
        
        model.addAttribute("utente", utenteCorrente);
        model.addAttribute("credenziali", utenteCorrente.getCredenziali());

        return "utente/profilo";
    }

   
    @GetMapping("/area-personale/prenotazioni")
    public String apriPrenotazioni(Model model) {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        Utente utenteCorrente = this.globalController.getUtente();
        // Passiamo la lista delle prenotazioni al Model
        List<Prenotazione> miePrenotazioni = this.prenotazioneService.getPrenotazioni(utenteCorrente);
        
        if(!miePrenotazioni.isEmpty() || miePrenotazioni!=null){
            model.addAttribute("prenotazioni", miePrenotazioni);
        }
        return "camera/prenotazioni";
    }
}
