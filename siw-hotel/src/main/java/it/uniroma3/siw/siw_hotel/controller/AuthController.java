package it.uniroma3.siw.siw_hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_hotel.dto.RegistrazioneDto;
import it.uniroma3.siw.siw_hotel.model.Credenziali;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.service.CredenzialiService;
import it.uniroma3.siw.siw_hotel.service.UtenteService;
import jakarta.transaction.Transactional;

@Controller
public class AuthController {

    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private CredenzialiService credenzialiService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/login")
    public String mostraLogin() {
        return "login"; // Cerca il file templates/login.html
    }
    
    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        // Passa al form il dto (oggetto che trasporta dati) vuoto
        model.addAttribute("registrazioneDto", new RegistrazioneDto());
        return "registrazione"; // Cerca templates/registrazione.html
    }

    @PostMapping("/registrazione")
    @Transactional
    public String registraUtente(@ModelAttribute("registrazioneDto") RegistrazioneDto dto, Model model) {
        
        // 0. CONTROLLO CONFERMA PASSWORD
        if (!dto.getPassword().equals(dto.getConfermaPassword())) {
            // Passiamo l'errore specifico alla vista
            model.addAttribute("errorePassword", "Le password inserite non coincidono. Riprova.");
            // ATTENZIONE: Ritorniamo la vista direttamente (niente "redirect:"), 
            // così l'oggetto "dto" rimane popolato e l'utente non perde i dati inseriti!
            return "registrazione"; 
        }

        // 1. CREIAMO LE CREDENZIALI
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(dto.getUsername());
        credenziali.setPassword(passwordEncoder.encode(dto.getPassword()));
        credenziali.setRuolo("DEFAULT_ROLE");
        
        // Salviamo le credenziali nel DB per generare il loro ID
        this.credenzialiService.saveCredenziali(credenziali);

        // 2. CREIAMO L'UTENTE ANAGRAFICO
        Utente utente = new Utente();
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setDataDiNascita(dto.getDataDiNascita());
        utente.setNazione(dto.getNazione());
        utente.setEmail(dto.getEmail());
        // Concatena il prefisso e il numero (magari con uno spazio in mezzo)
        String telefonoCompleto = dto.getPrefisso() + " " + dto.getTelefono();
        utente.setTelefono(telefonoCompleto);
        
        // 3. COLLEGHIAMO LE DUE ENTITÀ
        utente.setCredenziali(credenziali); // Qui usiamo l'oggetto appena salvato

        // Salviamo l'utente
        this.utenteService.salvaUtente(utente);

        return "redirect:/login?success";
    }
}
