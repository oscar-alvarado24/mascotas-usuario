package com.project.adoption.pet.infrastructure.exception;

public class CouldNotCommunicateWithAdoptionException extends RuntimeException {
    public CouldNotCommunicateWithAdoptionException(String message) {
        super(message);
    }
}
