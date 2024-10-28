package com.project.adoption.pet.infrastructure.output.pet.mapper;

import com.project.adoption.pet.domain.model.Pet;
import com.project.adoption.pet.infrastructure.output.pet.entity.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPetClientMapper {
    IPetClientMapper INSTANCE = Mappers.getMapper(IPetClientMapper.class);

    List<Pet> toPetList(List<PetEntity> petEntities);

    @Mapping(source = "type.name", target = "type")
    Pet toPet(PetEntity petEntity);
}
