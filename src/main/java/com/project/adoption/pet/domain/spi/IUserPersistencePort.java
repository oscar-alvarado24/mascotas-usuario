package com.project.adoption.pet.domain.spi;

import com.project.adoption.pet.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {
    String createUser(User user);

    User getUser(String email);

    String updateUser(String email, User user);

    String deleteUser(String email);

    List<User> getUserByCity(String city);

    List<User> getUserByNeighborhood(String neighborhood);
}
