package com.project.adoption.pet.infrastructure.output.dynamo.entity;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import com.project.adoption.pet.infrastructure.common.util.Constants;

@Setter
@DynamoDbBean
public class UserEntity {
    @Getter
    private String firstName;
    @Getter
    private String secondName;
    @Getter
    private String firstSurName;
    @Getter
    private String secondSurName;
    private String email;
    @Getter
    private String address;
    private String neighborhood;
    private String city;
    @Getter
    private String cellphone;
    @Getter
    private String status;

    @DynamoDbPartitionKey
    public String getEmail() {
        return email;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {Constants.CITY_INDEX})
    public String getCity() {
        return city;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {Constants.NEIGHBORHOOD_INDEX})
    public String getNeighborhood() {
        return neighborhood;
    }
}
