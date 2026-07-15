package it.uniroma3.siw.siw_hotel.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siw_hotel.dto.CambioPasswordDto;
import it.uniroma3.siw.siw_hotel.dto.ModificaRecensioneDto;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import it.uniroma3.siw.siw_hotel.service.RecensioneService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/recensioni")
    public String mostraRecensioni(Model model) {
        // Recuperiamo tutte le recensioni dal DB usando il Service che hai già creato
        List<Recensione> listaRecensioni = this.recensioneService.getRecensioniTutte();
        
        // Passiamo la lista alla pagina HTML
        model.addAttribute("recensioni", listaRecensioni);
        
        return "recensioni/recensioni"; // Cerca il file recensioni.html
    }
    
    @GetMapping("/area-personale/prenotazioni/{idPrenotazione}/recensisci")
    public String scriviRecensione(@PathVariable("idPrenotazione") Long idPrenotazione, Model model) {
        
        // Recupera la prenotazione dal DB
        Prenotazione prenotazione = this.prenotazioneService.getPrenotazione(idPrenotazione).get();
        
        // Sicurezza: Controlla che sia TERMINATA e che non abbia già una recensione
        if (!prenotazione.getStato().getNomeVisualizzato().equals("Terminata") || prenotazione.getRecensione() != null) {
            return "redirect:/area-personale/prenotazioni/" + idPrenotazione + "?errore=Impossibile recensire";
        }

        // Passa la prenotazione (per l'ID del tasto indietro) e un oggetto Recensione vuoto per il form
        model.addAttribute("prenotazione", prenotazione);
        model.addAttribute("recensione", new Recensione());
        
        return "recensioni/recensisci"; // Cerca il file recensioni.html
    }

    @PostMapping("/area-personale/prenotazioni/{idPrenotazione}/recensisci")
    public String salvaRecensione(@PathVariable("idPrenotazione") Long idPrenotazione, 
                                  @ModelAttribute("recensione") Recensione recensione) {


        Prenotazione prenotazione = this.prenotazioneService.getPrenotazione(idPrenotazione).get();
        // IL TEST DELLA VERITÀ: COSA È ARRIVATO DAL FORM?
        System.out.println("--- DEBUG RECENSIONE ---");
        System.out.println("Testo ricevuto: " + recensione.getTesto());
        System.out.println("Stelle ricevute: " + recensione.getNumeroStelle());
        System.out.println("------------------------");
        // Collega la recensione alla prenotazione (e viceversa se bidirezionale)
        recensione.setPrenotazione(prenotazione);
        prenotazione.setRecensione(recensione);
        
        // Se nel tuo DB hai anche l'utente collegato alla recensione:
        recensione.setCliente(prenotazione.getCliente());
        recensione.setDataEOra(LocalDateTime.now());

        // Salva nel database!
        this.recensioneService.saveRecensione(recensione); // Adatta al nome del tuo metodo
        
        // Rimanda l'utente alla pagina della prenotazione con un messaggio di successo
        return "redirect:/area-personale/prenotazioni/" + idPrenotazione + "?successo=Recensione inviata!";
    }


    @GetMapping("/area-personale/recensioni/{id}/modifica")
    public String mostraModificaRecensione(@PathVariable("id") Long id,Model model) {
        
        //recensione esistente
        Recensione recensioneEsistente = this.recensioneService.getRecensione(id);

        ModificaRecensioneDto dto = new ModificaRecensioneDto();
        dto.setTesto(recensioneEsistente.getTesto());
        dto.setNumeroStelle(recensioneEsistente.getNumeroStelle());

        model.addAttribute("modificaRecensioneDto", dto);
        model.addAttribute("id", id);
        
        return "recensioni/modifica_recensione"; // Cerca templates/cambio-password.html
    }

    // Salva modifica
    @PostMapping("/area-personale/recensioni/{id}/modifica")
    public String postMethodName(@PathVariable("id") Long id,@ModelAttribute("modificaRecensioneDto") ModificaRecensioneDto dto,Model model) {
        //recensione esistente da modificare
        Recensione recensioneDaModificare = this.recensioneService.getRecensione(id);

        recensioneDaModificare.setNumeroStelle(dto.getNumeroStelle());
        recensioneDaModificare.setTesto(dto.getTesto());
        
        this.recensioneService.saveRecensione(recensioneDaModificare);


        return "redirect:/area-personale/prenotazioni/" + recensioneDaModificare.getPrenotazione().getIdPrenotazione() + "?success=Modificata";
    }
    

}
