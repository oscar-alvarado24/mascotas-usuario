package com.project.adoption.pet.domain.api;

import com.project.adoption.pet.domain.model.Pet;

import java.util.List;
import java.util.Set;

public interface IPetServicePort {
    List<Pet> getPets(Set<Long> petIds);
}
