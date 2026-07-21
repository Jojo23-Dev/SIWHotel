package it.uniroma3.siw.siw_hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.siw_hotel.dto.RegistrazioneDto;
import it.uniroma3.siw.siw_hotel.dto.admin.AggiungiCameraDto;
import it.uniroma3.siw.siw_hotel.dto.admin.ModificaCameraDto;
import it.uniroma3.siw.siw_hotel.dto.admin.ModificaPrenotazioneDto;
import it.uniroma3.siw.siw_hotel.model.Camera;
import it.uniroma3.siw.siw_hotel.model.Credenziali;
import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.security.Ruolo;
import it.uniroma3.siw.siw_hotel.service.CameraService;
import it.uniroma3.siw.siw_hotel.service.PrenotazioneService;
import it.uniroma3.siw.siw_hotel.service.UtenteService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private UtenteService utenteService;


    @Autowired
    private CameraService cameraService;
    @GetMapping("/dashboard")
    public String mostraDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/prenotazioni")
    public String elencoPrenotazioni(@RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        Page<Prenotazione> paginaPrenotazioni = this.prenotazioneService.getUltimi10Prenotazioni(pagina);
        model.addAttribute("prenotazioni", paginaPrenotazioni.getContent());
        model.addAttribute("paginaCorrente", pagina);
        model.addAttribute("totalePagine", paginaPrenotazioni.getTotalPages());
        model.addAttribute("totaleElementi", paginaPrenotazioni.getTotalElements());
        return "admin/prenotazioni";
    }

    @GetMapping("/clienti")
    public String elencoClienti(@RequestParam(value = "pagina", defaultValue = "1") int pagina, Model model) {
        Page<Utente> paginaClienti = this.utenteService.getPrimi10Utenti(pagina);
        model.addAttribute("clienti", paginaClienti.getContent());
        model.addAttribute("paginaCorrente", pagina);
        model.addAttribute("totalePagine", paginaClienti.getTotalPages());
        model.addAttribute("totaleElementi", paginaClienti.getTotalElements());
        return "admin/clienti";
    }

    @GetMapping("/prenotazioni/{idPrenotazione}/modifica")
    public String mostraModificaPrenotazione(@PathVariable("idPrenotazione") Long idPrenotazione,Model model) {
        
        Prenotazione prenotazioneEsistente = this.prenotazioneService.getPrenotazione(idPrenotazione).get();

        ModificaPrenotazioneDto dto = new ModificaPrenotazioneDto();

        dto.setDataCheckIn(prenotazioneEsistente.getDataCheckIn());
        dto.setDataCheckOut(prenotazioneEsistente.getDataCheckOut());
        dto.setPrezzoTotale(prenotazioneEsistente.getPrezzoTotale());
        dto.setNote(prenotazioneEsistente.getNote());
        

        model.addAttribute("modificaPrenotazioneDto",dto);
        model.addAttribute("idPrenotazione",idPrenotazione);

        
        return "admin/modifica_prenotazione"; // Cerca templates/modifica_prenotazione.html
    }

    @PostMapping("/prenotazioni/{idPrenotazione}/modifica")
    public String modificaPrenotazione(@PathVariable("idPrenotazione") Long idPrenotazione,@ModelAttribute("modificaPrenotazioneDto") ModificaPrenotazioneDto dto,Model model) {
        //recensione esistente da modificare
        Prenotazione prenotazioneDaModificare = this.prenotazioneService.getPrenotazione(idPrenotazione).get();

        prenotazioneDaModificare.setDataCheckIn(dto.getDataCheckIn());
        prenotazioneDaModificare.setDataCheckOut(dto.getDataCheckOut());
        prenotazioneDaModificare.setPrezzoTotale(dto.getPrezzoTotale());
        prenotazioneDaModificare.setNote(dto.getNote());
        
        this.prenotazioneService.savePrenotazione(prenotazioneDaModificare);

        // FIX: la rotta "/admin/prenotazione/{id}" non esiste (il dettaglio prenotazione vive sotto
        // /area-personale/prenotazioni/{id}), quindi il redirect andava in 404. Torniamo all'elenco admin.
        return "redirect:/admin/prenotazioni?success=Modificata";
    }
    
    @GetMapping("/camere/{id}/modifica")
    public String mostraModificaCamera(@PathVariable("id") Long id,Model model) {
        Camera cameraEsistente = this.cameraService.getCamera(id).get();

        ModificaCameraDto dto = new ModificaCameraDto();

        dto.setPrezzo(cameraEsistente.getPrezzo());
        dto.setTipo(cameraEsistente.getTipo());
        dto.setDimensione(cameraEsistente.getDimensione());
        dto.setDescrizione(cameraEsistente.getDescrizione());
        dto.setLetti(cameraEsistente.getLetti());
        dto.setPersone(cameraEsistente.getPersone());
        

        model.addAttribute("modificaCameraDto",dto);
        model.addAttribute("id",id);

        
        return "admin/modifica_camera"; // Cerca templates/modifica_camera.html
    }

    @PostMapping("/camere/{id}/modifica")
    public String modificaCamera(@PathVariable("id") Long id,@ModelAttribute("modificaCameraDto") ModificaCameraDto dto,Model model) {
        //camera esistente da modificare
        Camera cameraDaModificare = this.cameraService.getCamera(id).get();

        
        cameraDaModificare.setPrezzo(dto.getPrezzo());
        cameraDaModificare.setTipo(dto.getTipo());
        cameraDaModificare.setDimensione(dto.getDimensione());
        cameraDaModificare.setDescrizione(dto.getDescrizione());
        cameraDaModificare.setLetti(dto.getLetti());
        cameraDaModificare.setPersone(dto.getPersone());
        
        this.cameraService.saveCamera(cameraDaModificare);


        return "redirect:/camere/" + cameraDaModificare.getId() + "?success=Modificata";
    }
    
    @GetMapping("/camere/aggiungi")
    public String mostraAggiungiCamera(Model model) {
        
        model.addAttribute("aggiungiCameraDto",new AggiungiCameraDto());
        return "admin/aggiungi_camera"; // Cerca templates/modifica_camera.html
    }

    @PostMapping("/camere/aggiungi")
    @Transactional
    public String aggiungiCamera(@ModelAttribute("aggiungiCameraDto") AggiungiCameraDto dto, Model model) {
        
        

        // 1. CREIAMO LA CAMERA
        Camera camera = new Camera();
        camera.setPrezzo(dto.getPrezzo());
        
        camera.setTipo(dto.getTipo());
        
        camera.setDimensione(dto.getDimensione());
        
        camera.setDescrizione(dto.getDescrizione());
        
        camera.setPersone(dto.getPersone());

        camera.setLetti(dto.getLetti());
        // Salviamo la camera nel DB per generare il loro ID
        this.cameraService.saveCamera(camera);

      
        return "redirect:/camere";
    }

    @PostMapping("/camere/{id}/elimina")
    @Transactional
    public String eliminaCamera(@PathVariable("id") Long id) {
        
        Camera cameraDaEliminare = this.cameraService.getCamera(id).get();

        // Eliminiamo la camera nel DB 
        this.cameraService.eliminaCamera(cameraDaEliminare);
        return "redirect:/camere";
    }

    @PostMapping("/prenotazioni/{idPrenotazione}/cancella")
    @Transactional
    public String cancellaPrenotazione(
       @PathVariable("idPrenotazione") Long idPrenotazione, // Prendi l'ID dall'URL
        RedirectAttributes redirectAttributes) {

        // prova a prendere la prenotazione dal database
        Optional<Prenotazione> prenotazioneOpt = this.prenotazioneService.getPrenotazione(idPrenotazione);

        // se opt è pieno continua
        if (prenotazioneOpt.isPresent()) {
            Prenotazione prenotazione = prenotazioneOpt.get();

            // sganciamo i collegamenti
            prenotazione.getCliente().rimuoviPrenotazione(prenotazione);
            prenotazione.getCamera().rimuoviPrenotazione(prenotazione);
                
            // annulliamo le Foreign Key
            prenotazione.setCliente(null);
            prenotazione.setCamera(null);

            this.prenotazioneService.cancellaPrenotazione(prenotazione);

            redirectAttributes.addFlashAttribute("messaggioSuccesso", "Prenotazione cancellata con successo.");
        } else {
            redirectAttributes.addFlashAttribute("errore", "Prenotazione non trovata nel sistema.");
        }
        return "redirect:/admin/prenotazioni?success=cancellata";
    }

}
