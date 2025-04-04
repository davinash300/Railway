package com.railway.trainservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;


@Data
public class TrainDTO {
    @NotBlank(message = "Train name is required")
    private String name;

    @NotBlank(message = "Train number is required")
    private String trainNumber;  // Ensure this field exists!

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    @NotBlank(message = "Departure time is required")
    private String departureTime;

    @NotBlank(message = "Arrival time is required")
    private String arrivalTime;

    @NotBlank(message = "Train type is required")
    private String trainType;

    @Positive(message = "Fare must be a positive number")
    private double fare;

    @Positive(message = "available seats must be a positive number")
    private int availableSeats;

    @NotNull(message = "Departure date is required")
    private LocalDate departureDate;
}
