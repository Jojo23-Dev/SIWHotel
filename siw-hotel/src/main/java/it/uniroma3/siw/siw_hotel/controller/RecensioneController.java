package it.uniroma3.siw.siw_hotel.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siw_hotel.dto.CambioPasswordDto;
import it.uniroma3.siw.siw_hotel.dto.ModificaRecensioneDto;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Recensione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import it.uniroma3.siw.siw_hotel.service.RecensioneService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class RecensioneController {

    @Autowired
    private RecensioneService recensioneService;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private GlobalController globalController;

    @GetMapping("/recensioni")
    public String mostraRecensioni(@RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        // Recuperiamo solo la pagina richiesta (10 recensioni più recenti) invece di tutte
        Page<Recensione> paginaRecensioni = this.recensioneService.getUltimi10Recensioni(pagina);

        // Passiamo la lista e i dati di paginazione alla pagina HTML
        model.addAttribute("recensioni", paginaRecensioni.getContent());
        model.addAttribute("paginaCorrente", pagina);
        model.addAttribute("totalePagine", paginaRecensioni.getTotalPages());
        model.addAttribute("totaleElementi", paginaRecensioni.getTotalElements());

        return "recensioni/recensioni"; // Cerca il file recensioni.html
    }

    @GetMapping("/area-personale/prenotazioni/{idPrenotazione}/recensisci")
    public String scriviRecensione(@PathVariable("idPrenotazione") Long idPrenotazione, Model model) {

        // Recupera la prenotazione dal DB
        Prenotazione prenotazione = this.prenotazioneService.getPrenotazione(idPrenotazione).get();

        // FIX: controllo di sicurezza mancante. Senza questo controllo chiunque fosse autenticato
        // poteva provare a scrivere una recensione su una prenotazione altrui conoscendone l'id.
        if (!isProprietario(prenotazione)) {
            return "redirect:/area-personale/prenotazioni?errore=Prenotazione non trovata nel sistema.";
        }

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

        // FIX: stesso controllo di ownership anche sul salvataggio (non solo sulla pagina del form),
        // altrimenti una POST diretta bypasserebbe comunque il controllo.
        if (!isProprietario(prenotazione)) {
            return "redirect:/area-personale/prenotazioni?errore=Prenotazione non trovata nel sistema.";
        }

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

        // FIX: controllo di sicurezza mancante. Senza questo controllo chiunque fosse autenticato
        // poteva modificare una recensione altrui conoscendone l'id.
        if (!isProprietario(recensioneEsistente)) {
            return "redirect:/area-personale/prenotazioni?errore=Recensione non trovata nel sistema.";
        }

        ModificaRecensioneDto dto = new ModificaRecensioneDto();
        dto.setTesto(recensioneEsistente.getTesto());
        dto.setNumeroStelle(recensioneEsistente.getNumeroStelle());

        model.addAttribute("modificaRecensioneDto", dto);
        model.addAttribute("id", id);

        return "recensioni/modifica_recensione"; // Cerca templates/modifica_password.html
    }

    // Salva modifica
    @PostMapping("/area-personale/recensioni/{id}/modifica")
    public String modificaRecensione(@PathVariable("id") Long id,@ModelAttribute("modificaRecensioneDto") ModificaRecensioneDto dto,Model model) {
        //recensione esistente da modificare
        Recensione recensioneDaModificare = this.recensioneService.getRecensione(id);

        // FIX: stesso controllo di ownership anche sul salvataggio.
        if (!isProprietario(recensioneDaModificare)) {
            return "redirect:/area-personale/prenotazioni?errore=Recensione non trovata nel sistema.";
        }

        recensioneDaModificare.setNumeroStelle(dto.getNumeroStelle());
        recensioneDaModificare.setTesto(dto.getTesto());

        this.recensioneService.saveRecensione(recensioneDaModificare);


        return "redirect:/area-personale/prenotazioni/" + recensioneDaModificare.getPrenotazione().getIdPrenotazione() + "?success=Modificata";
    }

    // Vero solo se la prenotazione appartiene all'utente attualmente loggato
    private boolean isProprietario(Prenotazione prenotazione) {
        Utente utenteCorrente = this.globalController.getUtente();
        return prenotazione != null && prenotazione.getCliente() != null
                && utenteCorrente != null
                && prenotazione.getCliente().getId().equals(utenteCorrente.getId());
    }

    // Vero solo se la recensione appartiene all'utente attualmente loggato
    private boolean isProprietario(Recensione recensione) {
        Utente utenteCorrente = this.globalController.getUtente();
        return recensione != null && recensione.getCliente() != null
                && utenteCorrente != null
                && recensione.getCliente().getId().equals(utenteCorrente.getId());
    }

}
