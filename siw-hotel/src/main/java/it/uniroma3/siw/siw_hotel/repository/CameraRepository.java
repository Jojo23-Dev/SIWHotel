package it.uniroma3.siw.siw_hotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.siw_hotel.model.Camera;

public interface CameraRepository extends JpaRepository<Camera,Long>{

    // Usata dall'elenco camere al posto di findAll(): restituisce solo la "pagina" richiesta (10 elementi)
    @Query("SELECT c FROM Camera c ORDER BY c.id ASC")
    Page<Camera> findPrimi10(Pageable pageable);

}
