package com.project.adoption.pet.infrastructure.exception;

public class UserNotUpdateException extends RuntimeException{
    public UserNotUpdateException (String message){
        super(message);
    }
}
