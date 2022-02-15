package it.unicam.cs.progetto.utils.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servizio utilizzato per ottenere le configurazioni globali dell'applicazione
 */
@Service
public class ApplicationSetting{
    @Autowired
    private ApplicationSettingRepository repository;

    public ApplicationSetting(){}

    public ApplicationSetting(ApplicationSettingRepository repository){
        this.repository = repository;
    }

    public ApplicationSettingsModel getSettings(){
        return repository.findById(1L).orElse(repository.save(new ApplicationSettingsModel(10)));
    }
}