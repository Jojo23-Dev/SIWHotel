package it.uniroma3.siw.siw_hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.repository.CredenzialiRepository;
import it.uniroma3.siw.siw_hotel.service.UtenteService;

// import it.uniroma3.siw.siw_hotel.repository.UtenteRepository;



@ControllerAdvice
public class GlobalController {
    
    // @Autowired
    // private UtenteRepository utenteRepository;

    // @ModelAttribute("userDetails")
    // public UserDetails getUtente() {
    //     UserDetails user = null;
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (!(authentication instanceof AnonymousAuthenticationToken)) {

    //         user  = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     }
    //     return user;
        
    // }

    // Iniettiamo i service/repository che ci servono per la nostra logica
    @Autowired
    private CredenzialiRepository credenzialiRepository;

    @Autowired
    private UtenteService utenteService;

    // "utente" sarà il nome usato da Thymeleaf es. ${utente.nome}
    @ModelAttribute("utente")
    public Utente getUtente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            
            // 1. Prendiamo lo username da UserDetails di Spring Security
            Object userDetails  = authentication.getPrincipal();
        
          if (userDetails instanceof UserDetails user) {
        // login classico (username/password)
          String username = user.getUsername();
            
            // 2. Troviamo l'ID dell'utente
            Utente utente = this.utenteService.getUtenteByUsername(username);
            
            if (utente!=null) {
                // 3. ESTRAIAMO L?OGGETTO UTENTE COL TUO METODO E LO RESTITUIAMO!
                return utente;
            }
      

        } else if (userDetails instanceof OidcUser oidcUser) {
        // login Google (OIDC)
        String email = oidcUser.getEmail();
        Utente utente = utenteService.getUtenteByEmail(email);
        if(utente==null){
             //.orElseGet(() -> creaNuovoUtenteDaGoogle(oidcUser));
        }
       
        return utente;
    } else if (userDetails instanceof OAuth2User oauth2User) {
        // fallback per provider OAuth2 non-OIDC
        return null;
    }
          
        }
        
        // Se l'utente non è loggato, restituisce null
        return null;
    }

}
