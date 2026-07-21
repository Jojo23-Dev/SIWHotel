package it.uniroma3.siw.siw_hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.siw_hotel.model.Prenotazione;
import it.uniroma3.siw.siw_hotel.model.Utente;
import it.uniroma3.siw.siw_hotel.model.state.StatoPrenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {

    // Ritorna il numero di prenotazioni che si sovrappongono con le date richieste per una specifica camera
    @Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.camera.id = :cameraId AND p.dataCheckIn < :dataCheckOut AND p.dataCheckOut > :dataCheckIn")
    int countSovrapposizioni(
        @Param("cameraId") Long cameraId,
        @Param("dataCheckIn") LocalDate dataCheckIn,
        @Param("dataCheckOut") LocalDate dataCheckOut
    );

    public List<Prenotazione> findByCliente(Utente utente);

    // Usate dalle viste con elenco al posto di findAll(): restituiscono solo la "pagina" richiesta (10 elementi)

    // Elenco admin: le prenotazioni più recenti per prime
    @Query("SELECT p FROM Prenotazione p ORDER BY p.idPrenotazione DESC")
    Page<Prenotazione> findUltimi10(Pageable pageable);

    // Storico prenotazioni di un singolo cliente: le più recenti per prime
    @Query("SELECT p FROM Prenotazione p WHERE p.cliente = :cliente ORDER BY p.idPrenotazione DESC")
    Page<Prenotazione> findUltimi10DiUtente(@Param("cliente") Utente cliente, Pageable pageable);

}

   
