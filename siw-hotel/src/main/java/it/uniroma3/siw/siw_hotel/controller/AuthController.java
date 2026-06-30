package it.uniroma3.siw.siw_hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String mostraLogin() {
        return "login"; // Cerca il file templates/login.html
    }
    
    @GetMapping("/registrazione")
    public String mostraRegistrazione() {
        return "registrazione"; // Cerca templates/registrazione.html
    }
}
