package it.uniroma3.siw.siw_hotel.dto;

public class CambioPasswordDto {
    
    private String passwordVecchia;
    private String passwordNuova;
    private String confermaPasswordNuova;

    public String getConfermaPasswordNuova() {
        return confermaPasswordNuova;
    }
    public void setConfermaPasswordNuova(String confermaPasswordNuova) {
        this.confermaPasswordNuova = confermaPasswordNuova;
    }
    public String getPasswordVecchia() {
        return passwordVecchia;
    }
    public void setPasswordVecchia(String passwordVecchia) {
        this.passwordVecchia = passwordVecchia;
    }
    public String getPasswordNuova() {
        return passwordNuova;
    }
    public void setPasswordNuova(String passwordNuova) {
        this.passwordNuova = passwordNuova;
    }

}
