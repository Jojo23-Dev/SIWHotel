package it.uniroma3.siw.siw_hotel.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siw_hotel.dto.DisponibilitaDto;
import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.service.CameraService;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import jakarta.transaction.Transactional;

@Controller
public class PrenotazioneController {

    @Autowired
    private GlobalController globalController;

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private CameraService cameraService;

    PrenotazioneController(GlobalController globalController) {
        this.globalController = globalController;
    }

    @GetMapping("/camere/{id}/prenota")
    public String prenotaCamera(@PathVariable("id") Long cameraId,
            Model model,
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        // 1. Sicurezza: se mancano le date, rimandiamo alla pagina della camera
        if (checkIn == null || checkOut == null) {
            return "redirect:/camere/" + cameraId;
        }

        // 2. Recuperiamo la camera (uso il tuo Optional come mi hai mostrato prima)
        Optional<Camera> cameraOptional = this.cameraService.getCamera(cameraId);
        if (cameraOptional.isEmpty()) {
            return "redirect:/camere";
        }
        Camera camera = cameraOptional.get();

        // 3. Calcoli logici del prezzo
        Long notti = ChronoUnit.DAYS.between(checkIn, checkOut);
        double prezzoTotale = notti * camera.getPrezzo();

        // 4. Passiamo tutto a Thymeleaf
        model.addAttribute("camera", camera);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("notti", notti);
        model.addAttribute("prezzoTotale", prezzoTotale);

        // 5. TRUCCO: Passiamo una lista vuota per non far crashare la tendina delle carte!
        model.addAttribute("carteUtente", List.of()); // Oppure Collections.emptyList()

        return "prenota"; // Cerca prenota.html
    }

    @PostMapping("/camere/{id}/prenota/conferma-prenotazione")
    @Transactional
    public String confermaPrenotazione(
        @PathVariable("id") Long cameraId, // <-- Cattura l'ID dall'URL
        @ModelAttribute("disponibilitaDto") DisponibilitaDto dto) {
        
        Utente utenteCorrente = this.globalController.getUtente();

        // 1. CREIAMO LA PRENOTAZIONE
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setCliente(utenteCorrente);
        prenotazione.setDataCheckIn(dto.getCheckIn());
        prenotazione.setDataCheckOut(dto.getCheckOut());
        
        Camera cameraCorrente = this.cameraService.getCamera(cameraId).get();
        prenotazione.setCamera(cameraCorrente);
        //manca prenotazione.note

        // Salviamo la prenotazione nel DB, si genererà l'ID
        this.prenotazioneService.savePrenotazione(prenotazione);

        return "redirect:/area-personale?success";
    }

}
