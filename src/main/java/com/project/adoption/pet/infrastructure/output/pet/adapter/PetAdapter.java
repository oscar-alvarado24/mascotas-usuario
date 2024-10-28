package com.project.adoption.pet.infrastructure.output.pet.adapter;

import com.project.adoption.pet.domain.model.Pet;
import com.project.adoption.pet.domain.spi.IPetPersistencePort;
import com.project.adoption.pet.infrastructure.output.pet.client.PetClient;
import com.project.adoption.pet.infrastructure.output.pet.mapper.IPetClientMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class PetAdapter implements IPetPersistencePort {
    private final PetClient petClient;
    private final IPetClientMapper petClientMapper;

    /**
     * @param petIds of pets belonging to the user
     * @return list of user pets
     */
    @Override
    public List<Pet> getPets(Set<Long> petIds) {
        return petClientMapper.toPetList(petClient.getPets(petIds));
    }
}
