package it.uniroma3.siw.siw_hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.siw_hotel.model.Utente;


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

    @GetMapping("/area-personale")
    public String apriAreaPersonale() {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        
        return "area_personale";
    }
}
