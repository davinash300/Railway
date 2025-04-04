package com.railway.searchservice.dto;

import lombok.Data;

@Data
public class TrainDTO {
    private String name;
    private String trainNumber;
    private String source;
    private String destination;
    private int capacity;
    private String departureTime;
    private String arrivalTime;
    private String trainType;
    private double fare;
    private int availableSeats;
}
