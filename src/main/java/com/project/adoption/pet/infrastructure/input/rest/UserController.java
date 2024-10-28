package com.project.adoption.pet.infrastructure.input.rest;

import com.project.adoption.pet.application.dto.UserRequest;
import com.project.adoption.pet.application.dto.UserResponse;
import com.project.adoption.pet.application.handler.IUserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {
    private final IUserHandler userHandler;

    @MutationMapping
    public String createUser(@Valid @Arguments UserRequest userRequest) {
        return userHandler.createUser(userRequest);
    }

    @MutationMapping
    public String updateUser( @Argument("email") String email, @Valid @Arguments UserRequest userRequest) {
        return userHandler.updateUser(email, userRequest);
    }

    @MutationMapping
    public String deleteUser( @Argument("email") String email) {
        return userHandler.deleteUser(email);
    }
    @QueryMapping
    public UserResponse getUser(@Argument("email") String email, @Argument("pet") boolean pet) {
        return userHandler.getUser(email, pet);
    }

    @QueryMapping
    public List<UserResponse> getUsersByCity(@Argument("city") String city, @Argument("pet") boolean pet) {
        return userHandler.getUserByCity(city, pet);
    }

    @QueryMapping
    public List<UserResponse> getUsersByNeighborhood(@Argument("neighborhood") String neighborhood, @Argument("pet") boolean pet) {
        return userHandler.getUserByNeighborhood(neighborhood, pet);
    }
}
