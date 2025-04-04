package com.railway.trainservice.exception;

public class DuplicateTrainNumberException extends RuntimeException {
    public DuplicateTrainNumberException(String message) {
        super(message);
    }
}
