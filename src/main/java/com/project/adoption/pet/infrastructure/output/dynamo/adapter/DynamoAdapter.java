package com.project.adoption.pet.infrastructure.output.dynamo.adapter;

import com.project.adoption.pet.domain.model.User;
import com.project.adoption.pet.domain.spi.IUserPersistencePort;
import com.project.adoption.pet.infrastructure.common.util.Constants;
import com.project.adoption.pet.infrastructure.exception.NotCreateUserException;
import com.project.adoption.pet.infrastructure.exception.UserNotDeletedException;
import com.project.adoption.pet.infrastructure.exception.UserNotFoundException;
import com.project.adoption.pet.infrastructure.exception.UserNotGetException;
import com.project.adoption.pet.infrastructure.exception.UserNotUpdateException;
import com.project.adoption.pet.infrastructure.exception.UsersByCityNotGetException;
import com.project.adoption.pet.infrastructure.exception.UsersByNeighborhoodNotGetException;
import com.project.adoption.pet.infrastructure.output.dynamo.config.DynamoDbManager;
import com.project.adoption.pet.infrastructure.output.dynamo.entity.UserEntity;
import com.project.adoption.pet.infrastructure.output.dynamo.mapper.IDynamoMapper;
import com.project.adoption.pet.infrastructure.output.dynamo.repository.IDynamoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DynamoAdapter implements IUserPersistencePort {

    private final IDynamoRepository dynamoRepository;
    private final IDynamoMapper dynamoMapper;
    /**
     * @param user to save
     * @return message of successfully creation
     */
    @Override
    public String createUser(User user) {
        try (DynamoDbManager manager = new DynamoDbManager()){
            evaluateStatusTable(manager);
            return dynamoRepository.saveUser(dynamoMapper.toUserEntity(user), manager.createTable());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new NotCreateUserException(String.format(Constants.MSG_NOT_CREATE_USER,user.getFirstName(),user.getFirstSurName()));
        }
    }

    /**
     * @param email of user to get
     * @return user with email searched
     */
    @Override
    public User getUser(String email) {
        try (DynamoDbManager manager = new DynamoDbManager()){
            UserEntity userEntity  = dynamoRepository.getUser(email, manager.createTable()).orElseThrow(()-> new UserNotFoundException(String.format(Constants.MSG_USER_NOT_FOUND,email)));
            return dynamoMapper.toUser(userEntity);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new UserNotGetException(String.format(Constants.MSG_USER_NOT_GET_EXCEPTION,email));
        }
    }

    /**
     * @param email of user to update
     * @param user info updated
     * @return message of successfully update
     */
    @Override
    public String updateUser(String email, User user) {
        try (DynamoDbManager manager = new DynamoDbManager()) {
            return dynamoRepository.updateUser(email, dynamoMapper.toUserEntity(user), manager.createTable());
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
            throw new UserNotUpdateException(String.format(Constants.MSG_USER_NOT_UPDATE_EXCEPTION,user.getFirstName(), user.getFirstSurName()));
        }
    }

    /**
     * @param email of user to delete
     * @return message of successfully inactivation
     */
    @Override
    public String deleteUser(String email) {
        try (DynamoDbManager manager = new DynamoDbManager()) {
            return dynamoRepository.deleteUser(email, manager.createTable());
        } catch (Exception e){
            log.error(e.getMessage(), e);
            throw new UserNotDeletedException(String.format(Constants.MSG_USER_NOT_DELETED_EXCEPTION, email));
        }
    }

    /**
     * @param city by search users
     * @return user list
     */
    @Override
    public List<User> getUserByCity(String city) {
        try (DynamoDbManager manager = new DynamoDbManager()){
            return dynamoMapper.toUserList(dynamoRepository.getUsersByCity(city, manager.createTable()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new UsersByCityNotGetException(String.format(Constants.MSG_USERS_BY_CITY_NOT_GET_EXCEPTION,city));
        }
    }

    /**
     * @param neighborhood by search users
     * @return user list
     */
    @Override
    public List<User> getUserByNeighborhood(String neighborhood) {
        try (DynamoDbManager manager = new DynamoDbManager()){
            return dynamoMapper.toUserList(dynamoRepository.getUsersByNeighborhood(neighborhood, manager.createTable()));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new UsersByNeighborhoodNotGetException(String.format(Constants.MSG_USERS_BY_NEIGHBORHOOD_NOT_GET_EXCEPTION,neighborhood));
        }
    }

    /**
     * method to validate if the table is created, if it exists it prints a message and if not it creates it
     * @param manager object that administer the client for connect to dynamo
     */
    private void evaluateStatusTable(DynamoDbManager manager) {
        try {
            manager.createTable().describeTable();
            log.info(Constants.TABLE_EXIST, Constants.TABLE_USER_NAME);
        } catch (ResourceNotFoundException exception) {
            log.info(Constants.TABLE_DONT_EXIST, Constants.TABLE_USER_NAME);
            dynamoRepository.createTableUser(manager.getEnhancedClient(), Constants.TABLE_USER_NAME);
        }
    }
}
