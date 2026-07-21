package it.uniroma3.siw.siw_hotel.controller;


import java.time.LocalDate;
import java.util.*;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.siw_hotel.dto.DisponibilitaDto;
import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.service.CameraService;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;

@Controller
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @Autowired
    private PrenotazioneService prenotazioneService;
    
    @GetMapping("/camere/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        // 1. Recuperi l'Optional dal service
        Optional<Camera> cameraOptional = cameraService.getCamera(id);
    
        // 2. CONTROLLA se esiste!
        if (cameraOptional.isPresent()) {
            // 3. Estratto l'oggetto reale (Camera)
            model.addAttribute("camera", cameraOptional.get());

            // Passiamo il DTO per il form delle date
            if (!model.containsAttribute("disponibilitaDto")) {
                model.addAttribute("disponibilitaDto", new DisponibilitaDto());
            }
            return "camera/cameraDettaglio"; // nome del tuo file HTML
        } else {
            // Gestisci il caso in cui l'id non esiste (es. redirect a errore o elenco)
            return "redirect:/camere";
        }
    }

    @GetMapping("/camere")
    public String list(@RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        Page<Camera> paginaCamere = this.cameraService.getPrimi10Camere(pagina);
        model.addAttribute("camere", paginaCamere.getContent());
        model.addAttribute("paginaCorrente", pagina);
        model.addAttribute("totalePagine", paginaCamere.getTotalPages());
        model.addAttribute("totaleElementi", paginaCamere.getTotalElements());
        return "camera/camere";
    }


    //TODO  @GetMapping("/") da vedere
    // public String listaCamereDisponibili( @ModelAttribute("disponibilitaDto") DisponibilitaDto dto,Model model) {
    //     Iterable<Camera> allCamere = this.cameraService.getCamereDisponibili(dto.getCheckIn(),dto.getCheckOut());
       
    //     model.addAttribute("camere", allCamere);
    //     return "/";
    // }

    // 2. NUOVO: Riceve le date e controlla il database
    @PostMapping("/camere/{id}/verifica-disponibilita")
    public String verificaDisponibilita(
        // TODO: Se è disponibile bottone Verde, altrimenti bottone Rosso

            @PathVariable("id") Long cameraId, 
            @ModelAttribute("disponibilitaDto") DisponibilitaDto dto,
            RedirectAttributes redirectAttributes) {

        // Controllo validità date base (es. check-out non può essere prima di check-in)
        if (dto.getCheckIn() == null || dto.getCheckOut() == null || dto.getCheckIn().isBefore(LocalDate.now()) || dto.getCheckOut().isBefore(dto.getCheckIn()) || dto.getCheckIn().isEqual(dto.getCheckOut())) {
            redirectAttributes.addFlashAttribute("errore", "Date non valide. Inserisci un periodo corretto.");
            return "redirect:/camere/" + cameraId;
        }

        // Controllo sovrapposizioni sul database
        int sovrapposizioni = this.prenotazioneService.contaSovrapposizioni(cameraId, dto.getCheckIn(), dto.getCheckOut());

        if (sovrapposizioni > 0) {
            // Camera OCCUPATA: torniamo indietro con un errore
            redirectAttributes.addFlashAttribute("errore", "Spiacenti, la camera è già occupata in queste date.");
            return "redirect:/camere/" + cameraId;
        }

       // Camera LIBERA: salviamo le date e aggiungiamo il MESSAGGIO VERDE
        redirectAttributes.addFlashAttribute("messaggioSuccesso", "Camera disponibile! Ora puoi procedere con la prenotazione."); // <-- DIFFERENZA 1: Aggiunto questo
        redirectAttributes.addFlashAttribute("checkInConfermato", dto.getCheckIn());
        redirectAttributes.addFlashAttribute("checkOutConfermato", dto.getCheckOut());
        
        // Torniamo alla STESSA PAGINA (che ora leggerà il messaggio e sbloccherà il bottone)
        return "redirect:/camere/" + cameraId;
    }
    
}
