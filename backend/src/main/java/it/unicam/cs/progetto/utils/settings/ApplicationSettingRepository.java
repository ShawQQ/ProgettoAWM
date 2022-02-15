package it.unicam.cs.progetto.utils.settings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationSettingRepository extends JpaRepository<ApplicationSettingsModel, Long> {
}
