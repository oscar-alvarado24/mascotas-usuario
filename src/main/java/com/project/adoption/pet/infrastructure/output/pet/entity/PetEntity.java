package com.project.adoption.pet.infrastructure.output.pet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PetEntity {
    private Long id;
    private String name;
    private int age;
    private String gender;
    private boolean available;
    private Type type;
}
