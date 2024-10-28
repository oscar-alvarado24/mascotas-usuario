package com.project.adoption.pet.domain.usecase;

import com.project.adoption.pet.domain.api.IPetServicePort;
import com.project.adoption.pet.domain.model.Pet;
import com.project.adoption.pet.domain.spi.IPetPersistencePort;

import java.util.List;
import java.util.Set;

public class PetUseCase implements IPetServicePort {

    private final IPetPersistencePort petPersistencePort;

    public PetUseCase(IPetPersistencePort petPersistencePort) {
        this.petPersistencePort = petPersistencePort;
    }

    /**
     * @param petIds associated to user
     * @return list of user's pets
     */
    @Override
    public List<Pet> getPets(Set<Long> petIds) {
        return petPersistencePort.getPets(petIds);
    }
}
