package it.uniroma3.siw.siw_hotel.dto;

import java.time.LocalDate;

public class RegistrazioneDto {
    // Campi dell'Utente
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String nazione;
    private String email;

    private String prefisso; // verrà unito in "utente.telefono"
    private String telefono;

    // Campi delle Credenziali
    private String username;
    private String password;



    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }
    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
    public String getNazione() {
        return nazione;
    }
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPrefisso() {
        return prefisso;
    }
    public void setPrefisso(String prefisso) {
        this.prefisso = prefisso;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
