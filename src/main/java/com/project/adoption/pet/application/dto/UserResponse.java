package com.project.adoption.pet.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String username;
    private String email;
    private String address;
    private String neighborhood;
    private String city;
    private String cellphone;
    private List<PetResponse> pets;
}