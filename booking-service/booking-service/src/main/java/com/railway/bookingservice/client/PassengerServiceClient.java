package com.railway.bookingservice.client;

import com.railway.bookingservice.dto.PassengerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "passenger-service", url = "http://localhost:8084/api/passengers")
public interface PassengerServiceClient {

    @GetMapping("/{id}")
    PassengerDTO getPassengerById(@PathVariable Long id);

    @PostMapping
    PassengerDTO addPassenger(@RequestBody PassengerDTO passengerDTO);
}
