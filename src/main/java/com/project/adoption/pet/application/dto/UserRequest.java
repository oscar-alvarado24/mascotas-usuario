package com.project.adoption.pet.application.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequest {
    private String firstName;
    private String secondName;
    private String firstSurName;
    private String secondSurName;
    @Email(message = "Por favor, ingrese una dirección de correo electrónico válida")
    private String email;
    private String address;
    private String neighborhood;
    private String city;
    private String cellphone;
}
