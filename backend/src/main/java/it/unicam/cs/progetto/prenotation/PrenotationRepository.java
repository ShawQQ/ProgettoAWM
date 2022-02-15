package it.unicam.cs.progetto.prenotation;

import it.unicam.cs.progetto.prenotation.enums.PrenotationDuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PrenotationRepository extends JpaRepository<PrenotationModel, Long> {
    Optional<List<PrenotationModel>> findAllPrenotationByDateAndDuration(Date prenotationDate, PrenotationDuration PrenotationDuration);
}
