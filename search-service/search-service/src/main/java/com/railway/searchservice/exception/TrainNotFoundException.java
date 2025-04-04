package com.railway.searchservice.exception;

public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(String trainNumber) {
        super("Train not found with train number: " + trainNumber);
    }
}
