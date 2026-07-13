package it.uniroma3.siw.siw_hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_hotel.dto.ModificaDatiPersonaliDto;
import it.uniroma3.siw.siw_hotel.dto.CambioPasswordDto;
import it.uniroma3.siw.siw_hotel.dto.RegistrazioneDto;
import it.uniroma3.siw.siw_hotel.model.Credenziali;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.service.CredenzialiService;
import it.uniroma3.siw.siw_hotel.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
public class AuthController {

    private final UtenteController utenteController;

    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private CredenzialiService credenzialiService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GlobalController globalController;

    AuthController(UtenteController utenteController) {
        this.utenteController = utenteController;
    }

    @GetMapping("/login")
    public String mostraLogin() {
        return "auth/login"; // Cerca il file templates/login.html
    }
    
    @GetMapping("/registrazione")
    public String mostraRegistrazione(Model model) {
        // Passa al form il dto (oggetto che trasporta dati) vuoto
        model.addAttribute("registrazioneDto", new RegistrazioneDto());
        return "auth/registrazione"; // Cerca templates/registrazione.html
    }

    @GetMapping("/area-personale/profilo/modifica-dati-personali")
    public String mostraModificaDatiPersonali(Model model) {
        // Passa al form il dto (oggetto che trasporta dati) vuoto
      
        Utente utenteCorrente = this.globalController.getUtente();
        
        ModificaDatiPersonaliDto modifica = new ModificaDatiPersonaliDto();
        
        
        modifica.setNome(utenteCorrente.getNome());
        modifica.setCognome(utenteCorrente.getCognome());
        modifica.setDataDiNascita(utenteCorrente.getDataDiNascita());
        modifica.setNazione(utenteCorrente.getNazione());
        modifica.setEmail(utenteCorrente.getEmail());
        if(utenteCorrente.getTelefono().length()==15){
            modifica.setTelefono(utenteCorrente.getTelefono().substring(4,utenteCorrente.getTelefono().length()));
            modifica.setPrefisso(utenteCorrente.getTelefono().substring(0,3));
        }
        else{
            modifica.setTelefono(utenteCorrente.getTelefono().substring(3,utenteCorrente.getTelefono().length()));
            modifica.setPrefisso(utenteCorrente.getTelefono().substring(0,2));
        
        }
        model.addAttribute("modificaDto",modifica);
        
        return "utente/modifica_dati_personali"; // Cerca templates/modifica_dati_personali.html
    }

     @GetMapping("/cambio-password")
    public String mostraModificaPassword(Model model) {
        // Passa al form il dto (oggetto che trasporta dati) vuoto
        model.addAttribute("cambioPasswordDto", new CambioPasswordDto());
        return "auth/cambio_password"; // Cerca templates/cambio-password.html
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
            return "auth/registrazione"; 
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

    @PostMapping("/profilo/modifica-dati-personali")
    @Transactional
    public String modificaDatiPersonali(@ModelAttribute("modificaDto") ModificaDatiPersonaliDto dto, Model model) {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        Utente utenteCorrente = this.globalController.getUtente();
        
        //utente che si trova nel DB
        Utente utente = this.utenteService.getUtente(utenteCorrente.getId()).get();

        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setDataDiNascita(dto.getDataDiNascita());
        utente.setNazione(dto.getNazione());
        utente.setEmail(dto.getEmail());
        // Concatena il prefisso e il numero (magari con uno spazio in mezzo)
        String telefonoCompleto = dto.getPrefisso() + " " + dto.getTelefono();
        utente.setTelefono(telefonoCompleto);

        return "redirect:/area-personale?success";
    }
    @PostMapping("/profilo/cambio-password")
    @Transactional
    public String cambiaPassword(@ModelAttribute("cambioPasswordDto") CambioPasswordDto dto, Model model) {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        Utente utenteCorrente = this.globalController.getUtente();
        Credenziali credenziali = utenteCorrente.getCredenziali();

         // 0. CONTROLLO CONFERMA PASSWORD
        if (!dto.getPasswordNuova().equals(dto.getConfermaPasswordNuova())) {
            // Passiamo l'errore specifico alla vista
            model.addAttribute("errorePassword", "Le password inserite non coincidono. Riprova.");
            // ATTENZIONE: Ritorniamo la vista direttamente (niente "redirect:"), 
            // così l'oggetto "dto" rimane popolato e l'utente non perde i dati inseriti!
            return "auth/cambio_password"; 
        }

        // CONTROLLO SE LA VECCHIA PASSWORD E' UGUALE A QUELLA NUOVA
        if(!this.passwordEncoder.matches(dto.getPasswordVecchia() ,credenziali.getPassword())){
             model.addAttribute("errorePassword", "La password attuale non è corretta.");
            // ATTENZIONE: Ritorniamo la vista direttamente (niente "redirect:"), 
            // così l'oggetto "dto" rimane popolato e l'utente non perde i dati inseriti!
            return "auth/cambio_password"; 
        }

        credenziali.setPassword(passwordEncoder.encode(dto.getPasswordNuova()));
        // Salviamo le credenziali nel DB per generare il loro ID
        this.credenzialiService.saveCredenziali(credenziali);
        
        return "redirect:/area-personale?success";
    }




   
    @PostMapping("/area-personale/profilo/elimina")
    @Transactional
    public String eliminaProfillo(HttpServletRequest request) {
        // Non devi fare assolutamente nulla qui!
        // L'oggetto "utente" è già stato inserito nel Model dal GlobalController.
        Utente utenteCorrente = this.globalController.getUtente();
        
        this.utenteService.eliminaUtente(utenteCorrente);
        

        // PASSAGGIO CRITICO: Eseguiamo il LOGOUT forzato!
        // Visto che l'utente non esiste più, dobbiamo distruggere la sua sessione 
        // per evitare che Spring Security vada in crash alla pagina successiva.
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:/";
    }
}
