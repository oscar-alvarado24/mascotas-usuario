package com.project.adoption.pet.infrastructure.output.dynamo.repository;

import com.project.adoption.pet.infrastructure.output.dynamo.entity.UserEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDynamoRepository {
    String saveUser(UserEntity user, DynamoDbTable<UserEntity> table);
    Optional<UserEntity> getUser(String email, DynamoDbTable<UserEntity> table);
    List<UserEntity> getUserByEmails(List<String> emails, DynamoDbTable<UserEntity> table, DynamoDbEnhancedClient enhancedClient);
    List<UserEntity> getUsersByCity(String city, DynamoDbTable<UserEntity> table);
    List<UserEntity> getUsersByNeighborhood(String neighborhood, DynamoDbTable<UserEntity> table);
    String deleteUser(String email, DynamoDbTable<UserEntity> table);
    String updateUser(String email, UserEntity user, DynamoDbTable<UserEntity> table);
    void createTableUser(DynamoDbEnhancedClient enhancedClient, String tableName);
}
