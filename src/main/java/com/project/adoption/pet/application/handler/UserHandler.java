package com.project.adoption.pet.application.handler;

import com.project.adoption.pet.application.dto.PetResponse;
import com.project.adoption.pet.application.dto.UserRequest;
import com.project.adoption.pet.application.dto.UserResponse;
import com.project.adoption.pet.application.mapper.IPetMapper;
import com.project.adoption.pet.application.mapper.IUserMapper;
import com.project.adoption.pet.domain.api.IAdoptionServicePort;
import com.project.adoption.pet.domain.api.IPetServicePort;
import com.project.adoption.pet.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final IAdoptionServicePort adoptionServicePort;
    private final IPetServicePort petServicePort;
    private final IUserMapper userMapper;
    private final IPetMapper petMapper;

    /**
     * @param userRequest information of user to create
     * @return message of successfully creation
     */
    @Override
    public String createUser(UserRequest userRequest) {
        return userServicePort.createUser(userMapper.toUser(userRequest));
    }

    /**
     * @param email of user to get
     * @param pet   Flag indicating whether or not the user's pets are required
     * @return user with the requested information
     */
    @Override
    public UserResponse getUser(String email, boolean pet) {
        UserResponse user = userMapper.toUserRequest(userServicePort.getUser(email));
        List<PetResponse> pets = new ArrayList<>();
        if (pet) {
            Map<Long, LocalDate> petsInfo = adoptionServicePort.getPetsId(email);
            pets = petMapper.toPetResponseList(petServicePort.getPets(petsInfo.keySet()), petsInfo);
        }
        user.setPets(pets);
        return user;
    }

    /**
     * @param email of user to update
     * @param user  with info updated
     * @return message of successfully update
     */
    @Override
    public String updateUser(String email, UserRequest user) {
        return userServicePort.updateUser(email, userMapper.toUser(user));
    }

    /**
     * @param email of user to delete
     * @return message of successfully deleted
     */
    @Override
    public String deleteUser(String email) {
        return userServicePort.deleteUser(email);
    }

    /**
     * @param city by search users
     * @param pet Flag indicating whether or not the user's pets are required
     * @return user list
     */
    @Override
    public List<UserResponse> getUserByCity(String city, boolean pet) {
        List<UserResponse> users = userMapper.toUserRequestList(userServicePort.getUserByCity(city));
        getRetListForUsers(pet, users);
        return users;
    }

    /**
     * @param neighborhood by search users
     * @param pet Flag indicating whether or not the user's pets are required
     * @return user list
     */
    @Override
    public List<UserResponse> getUserByNeighborhood(String neighborhood, boolean pet) {
        List<UserResponse> users = userMapper.toUserRequestList(userServicePort.getUserByNeighborhood(neighborhood));
        getRetListForUsers(pet, users);
        return users;
    }

    /**
     * First, it looks for the pets that correspond to each user in the adoption table, then it obtains the information
     * about those pets and finally it sets them to each user.
     * @param pet Flag indicating whether or not the user's pets are required
     * @param users user list
     */
    private void getRetListForUsers(boolean pet, List<UserResponse> users) {
        Map<String, List<PetResponse>> pets;
        if (pet) {
            List<String> emails = users.stream()
                    .map(UserResponse::getEmail)
                    .toList();
            Map<String, Map<Long, LocalDate>> petsInfo = adoptionServicePort.getPetsIdByEmails(emails);
            Set<Long> petsId = new HashSet<>();
            petsInfo.forEach((k, v) -> petsId.addAll(v.keySet()));
            pets = petMapper.toPetResponseListForMultiplesUsers(petServicePort.getPets(petsId), petsInfo);
        } else {
            pets = new HashMap<>();
        }
        users.forEach(userResponse -> userResponse.setPets(pet ? pets.get(userResponse.getEmail()) : new ArrayList<>()));
    }
}
