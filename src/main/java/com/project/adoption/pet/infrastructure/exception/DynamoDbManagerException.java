package com.project.adoption.pet.infrastructure.exception;

public class DynamoDbManagerException extends RuntimeException {
    public DynamoDbManagerException(String message) {
        super(message);
    }
}
