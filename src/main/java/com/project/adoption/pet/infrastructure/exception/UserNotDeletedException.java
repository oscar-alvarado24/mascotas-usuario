package com.project.adoption.pet.infrastructure.exception;

public class UserNotDeletedException extends RuntimeException {
    public UserNotDeletedException(String message) {
        super(message);
    }
}
