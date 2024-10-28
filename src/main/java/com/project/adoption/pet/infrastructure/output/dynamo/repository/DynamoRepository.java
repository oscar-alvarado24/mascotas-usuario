package com.project.adoption.pet.infrastructure.output.dynamo.repository;

import com.project.adoption.pet.infrastructure.common.util.Constants;
import com.project.adoption.pet.infrastructure.exception.NotCreateUserTableException;
import com.project.adoption.pet.infrastructure.output.dynamo.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchGetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchGetResultPage;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedGlobalSecondaryIndex;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ReadBatch;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DynamoRepository implements IDynamoRepository{
    /**
     * @param user to create
     * @return message of successfully creation
     */
    @Override
    public String saveUser(UserEntity user, DynamoDbTable<UserEntity> table) {
        table.putItem(user);
        return String.format(Constants.MSG_CREATE_SUCCESSFULLY,user.getFirstName(), user.getFirstSurName());
    }

    /**
     * @param email of user to get
     * @return user with email searched
     */
    @Override
    public Optional<UserEntity> getUser(String email, DynamoDbTable<UserEntity> table) {
        Key key = Key.builder().partitionValue(email).build();
        return Optional.ofNullable(table.getItem(key));
    }

    /**
     * @param emails of users to get
     * @return user list
     */
    @Override
    public List<UserEntity> getUserByEmails(List<String> emails, DynamoDbTable<UserEntity> table, DynamoDbEnhancedClient enhancedClient) {
        ReadBatch.Builder<UserEntity> readBatchBuilder = ReadBatch.builder(UserEntity.class)
                .mappedTableResource(table);

        for (String email : emails) {
            readBatchBuilder.addGetItem(Key.builder().partitionValue(email).build());
        }

        BatchGetItemEnhancedRequest batchGetRequest = BatchGetItemEnhancedRequest.builder()
                .readBatches(readBatchBuilder.build())
                .build();

        List<UserEntity> results = new ArrayList<>();

        for (BatchGetResultPage resultPage : enhancedClient.batchGetItem(batchGetRequest)) {
            List<UserEntity> pageResults = resultPage.resultsForTable(table);
            results.addAll(pageResults);
        }

        return results;
    }

    /**
     * @param city by search users
     * @return user list
     */
    @Override
    public List<UserEntity> getUsersByCity(String city, DynamoDbTable<UserEntity> table) {
        return getUsersByIndex(Constants.CITY_INDEX,city,table);
    }

    /**
     * @param neighborhood by search users
     * @return user list
     */
    @Override
    public List<UserEntity> getUsersByNeighborhood(String neighborhood, DynamoDbTable<UserEntity> table) {
        return getUsersByIndex(Constants.NEIGHBORHOOD_INDEX, neighborhood,table);
    }

    /**
     * @param email of user to delete
     * @return message of successfully inactivation
     */
    @Override
    public String deleteUser(String email, DynamoDbTable<UserEntity> table) {
        Optional<UserEntity> userEntity = getUser(email,table);
        if(userEntity.isPresent()) {
            UserEntity userToDelete = userEntity.get();
            userToDelete.setStatus(Constants.INACTIVE);
            table.updateItem(userToDelete);
            return String.format(Constants.MSG_DELETE_SUCCESSFULLY, userToDelete.getFirstName(), userToDelete.getFirstSurName());
        }
        return String.format(Constants.EMAIL_NOT_FOUND,email);
    }

    /**
     *
     * @param email of user to update
     * @param user info updated
     * @param table object to operate the table dynamo
     * @return message of successfully update
     */
    @Override
    public String updateUser(String email, UserEntity user, DynamoDbTable<UserEntity> table) {
        Optional<UserEntity> userEntity = getUser(email,table);
        String response;
        if(userEntity.isPresent()) {
            UserEntity userToUpdate = updateUser(user, userEntity.get());
            table.updateItem(userToUpdate);
            response = String.format(Constants.MSG_USER_UPDATE_SUCCESSFULLY, userToUpdate.getFirstName(), userToUpdate.getFirstSurName());
        }else {
            saveUser(user,table);
            response = String.format(Constants.MSG_UPDATE_USER_NOT_FOUND,email);
        }
        return response;
    }

    /**
     * @param enhancedClient client of DynamoDbEnhancedClient for work in dynamoDB
     * @param tableName table's name to crete
     */
    @Override
    public void createTableUser(DynamoDbEnhancedClient enhancedClient, String tableName) {
        try {
            EnhancedGlobalSecondaryIndex cityGsi = createIndex(Constants.CITY_INDEX);
            EnhancedGlobalSecondaryIndex neighborhoodGsi = createIndex(Constants.NEIGHBORHOOD_INDEX);

            CreateTableEnhancedRequest createTableRequest = CreateTableEnhancedRequest.builder()
                    .globalSecondaryIndices(cityGsi, neighborhoodGsi)
                    .provisionedThroughput(p ->
                            p.readCapacityUnits(2L)
                                    .writeCapacityUnits(2L)
                                    .build())
                    .build();

            enhancedClient.table(tableName, TableSchema.fromBean(UserEntity.class)).createTable(createTableRequest);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new NotCreateUserTableException(exception.getMessage());
        }
    }

    private EnhancedGlobalSecondaryIndex createIndex(String indexName){
        return EnhancedGlobalSecondaryIndex.builder()
                .indexName(indexName)
                .projection(p -> p.projectionType(ProjectionType.ALL))
                .provisionedThroughput(p->
                        p.readCapacityUnits(2L)
                                .writeCapacityUnits(2L)
                                .build())
                .build();
    }


    private static UserEntity updateUser(UserEntity user, UserEntity userToUpdate) {
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setSecondName(user.getSecondName() == null ? null : user.getSecondName());
        userToUpdate.setFirstSurName(user.getFirstSurName());
        userToUpdate.setSecondSurName(user.getFirstSurName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setStatus(user.getStatus());
        userToUpdate.setCity(user.getCity());
        userToUpdate.setNeighborhood(user.getNeighborhood());
        userToUpdate.setCellphone(user.getCellphone());
        return userToUpdate;
    }

    private List<UserEntity> getUsersByIndex(String index, String valueSearch, DynamoDbTable<UserEntity> table){
        DynamoDbIndex<UserEntity> dbIndex = table.index(index);

        // Perform the query for the attribute
        QueryConditional queryConditionalA = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(valueSearch)
                .build());

        SdkIterable<Page<UserEntity>> queryResult = dbIndex.query(r -> r.queryConditional(queryConditionalA));
        return queryResult.stream()
                .parallel()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }
}
