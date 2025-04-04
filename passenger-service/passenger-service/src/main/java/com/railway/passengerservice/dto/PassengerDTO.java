package com.railway.passengerservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PassengerDTO {
    private Long id;
    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String email;
    private String phoneNumber;

  }
