package com.project.adoption.pet.domain.spi;

import com.project.adoption.pet.domain.model.Pet;

import java.util.List;
import java.util.Set;

public interface IPetPersistencePort {
    List<Pet> getPets(Set<Long> petIds);
}
