package com.railway.bookingservice.client;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.railway.bookingservice.dto.TrainDTO;

@FeignClient(name = "train-service", url = "http://localhost:8081/api/trains")
public interface TrainServiceClient {
	
	@GetMapping("/{id}")
	TrainDTO getTrainById(@PathVariable("id") Long id);


    @PutMapping("/update-seats")
    void updateSeats(@RequestParam String trainNumber, 
                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                     @RequestParam int bookedSeats);
    
    @GetMapping("/number/{trainNumber}")
    public TrainDTO getTrainByNumber(@PathVariable String trainNumber);

}

