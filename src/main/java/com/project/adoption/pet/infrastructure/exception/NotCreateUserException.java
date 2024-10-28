package com.project.adoption.pet.infrastructure.exception;

public class NotCreateUserException extends RuntimeException {
    public NotCreateUserException(String message){
        super(message);
    }
}
