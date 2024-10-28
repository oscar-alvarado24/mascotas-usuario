package com.project.adoption.pet.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetResponse {
    private String name;
    private int age;
    private String type;
    private String gender;
    private String adoptionDate;
}
