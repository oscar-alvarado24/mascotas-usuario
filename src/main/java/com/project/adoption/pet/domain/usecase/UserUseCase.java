package com.project.adoption.pet.domain.usecase;

import com.project.adoption.pet.domain.api.IUserServicePort;
import com.project.adoption.pet.domain.model.User;
import com.project.adoption.pet.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    /**
     * @param user to create
     * @return message of successfully creation
     */
    @Override
    public String createUser(User user) {
        return userPersistencePort.createUser(user);
    }

    /**
     * @param email of user to get
     * @return user corresponding to email
     */
    @Override
    public User getUser(String email) {
        return userPersistencePort.getUser(email);
    }

    /**
     * @param email of user to update
     * @param user with info updated
     * @return message of successfully updated
     */
    @Override
    public String updateUser(String email, User user) {
        return userPersistencePort.updateUser(email, user);
    }

    /**
     * @param email of user to deleted
     * @return message of successfully deleted
     */
    @Override
    public String deleteUser(String email) {
        return userPersistencePort.deleteUser(email);
    }

    /**
     * @param city by search users
     * @return user list
     */
    @Override
    public List<User> getUserByCity(String city) {
        return userPersistencePort.getUserByCity(city);
    }

    /**
     * @param neighborhood by search users
     * @return user list
     */
    @Override
    public List<User> getUserByNeighborhood(String neighborhood) {
        return userPersistencePort.getUserByNeighborhood(neighborhood);
    }
}
