package com.project.adoption.pet.application.handler;

import com.project.adoption.pet.application.dto.UserRequest;
import com.project.adoption.pet.application.dto.UserResponse;

import java.util.List;

public interface IUserHandler {
    String createUser(UserRequest user);

    UserResponse getUser(String email, boolean pet);

    String updateUser(String email, UserRequest user);

    String deleteUser(String email);

    List<UserResponse> getUserByCity(String email, boolean pet);

    List<UserResponse> getUserByNeighborhood(String email, boolean pet);
}
