package com.project.adoption.pet.domain.usecase;

import com.project.adoption.pet.domain.api.IAdoptionServicePort;
import com.project.adoption.pet.domain.spi.IAdoptionPersistencePort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AdoptionUseCase implements IAdoptionServicePort {

    private final IAdoptionPersistencePort adoptionPersistencePort;

    public AdoptionUseCase(IAdoptionPersistencePort adoptionPersistencePort) {
        this.adoptionPersistencePort = adoptionPersistencePort;
    }

    /**
     * @param userEmail from whom you want to obtain the adoptions
     * @return list od pets id
     */
    @Override
    public Map<Long, LocalDate> getPetsId(String userEmail) {
        return adoptionPersistencePort.getPetsId(userEmail);
    }

    /**
     * @param emails
     * @return
     */
    @Override
    public Map<String, Map<Long, LocalDate>> getPetsIdByEmails(List<String> emails) {
        return adoptionPersistencePort.getPetsIdByEmails(emails);
    }
}
