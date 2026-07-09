package it.uniroma3.siw.siw_hotel.dto;

import java.time.LocalDate;


public class DisponibilitaDto {
    private Long cameraId;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private double prezzoTotale;

    // Genera qui i costruttori, Getter e Setter!

    public double getPrezzoTotale() {
        return prezzoTotale;
    }
    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }
    public Long getCameraId() {
        return this.cameraId;
    }
    public void setCamera(Long cameraId) {
        this.cameraId = cameraId;
    }
    public LocalDate getCheckIn() { return dataCheckIn; }
    public void setCheckIn(LocalDate dataCheckIn) { this.dataCheckIn = dataCheckIn; }
    public LocalDate getCheckOut() { return dataCheckOut; }
    public void setCheckOut(LocalDate dataCheckOut) { this.dataCheckOut = dataCheckOut; }
}
