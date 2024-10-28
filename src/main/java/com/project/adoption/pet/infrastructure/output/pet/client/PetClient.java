package com.project.adoption.pet.infrastructure.output.pet.client;

import com.project.adoption.pet.infrastructure.output.pet.entity.PetEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@FeignClient(name = "PetClient", url = "${pet.service.url}")
public interface PetClient {
    @GetMapping("/get-pets")
    List<PetEntity> getPets(Set<Long> petsId);
}
