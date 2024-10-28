package com.project.adoption.pet.application.mapper;

import com.project.adoption.pet.application.dto.PetResponse;
import com.project.adoption.pet.domain.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPetMapper {
    IPetMapper INSTANCE = Mappers.getMapper(IPetMapper.class);

    default List<PetResponse> toPetResponseList(List<Pet> pets, Map<Long, LocalDate> adoptionInfo){
        if (pets == null || pets.isEmpty()) return new ArrayList<>();
        return pets.stream()
                .map(pet->toPetResponse(pet,adoptionInfo.get(pet.getId())))
                .toList();
    }
    default PetResponse toPetResponse(Pet pet,LocalDate adoptionDate){
        if (pet == null) return null;
        PetResponse petResponse = new PetResponse();
        petResponse.setName(pet.getName());
        petResponse.setAdoptionDate(adoptionDate.toString());
        petResponse.setAge(pet.getAge());
        petResponse.setGender(pet.getGender());
        return petResponse;
    }

    default Map<String, List<PetResponse>> toPetResponseListForMultiplesUsers(List<Pet> pets,Map<String,Map<Long, LocalDate>> petsInfo){
        return petsInfo.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            Map<Long, LocalDate> userPetsAdoption = entry.getValue();

                            return pets.stream()
                                    .parallel()
                                    .filter(pet -> userPetsAdoption.containsKey(pet.getId()))
                                    .map(pet -> toPetResponse(pet, userPetsAdoption.get(pet.getId())))
                                    .collect(Collectors.toList());
                        }
                ));
    }

}
