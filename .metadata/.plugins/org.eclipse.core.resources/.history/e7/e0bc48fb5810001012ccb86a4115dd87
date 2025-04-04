package com.railway.trainservice.controller;

import com.railway.trainservice.dto.SeatAvailabilityDTO;
import com.railway.trainservice.entity.SeatAvailability;
import com.railway.trainservice.service.SeatAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/seat-availability")
public class SeatAvailabilityController {
    private static final Logger log = LoggerFactory.getLogger(SeatAvailabilityController.class);

    @Autowired
    private SeatAvailabilityService seatAvailabilityService;

    @PostMapping
    public ResponseEntity<SeatAvailability> saveSeatAvailability(@RequestBody SeatAvailabilityDTO seatAvailabilityDTO) {
       // log.info("Saving seat availability for train {} on date {}", seatAvailabilityDTO.getTrainNumber(), seatAvailabilityDTO.getTravelDate());
        SeatAvailability savedData = seatAvailabilityService.saveSeatAvailability(seatAvailabilityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
    }

}

