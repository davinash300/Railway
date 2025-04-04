package com.railway.passengerservice.controller;

import com.railway.passengerservice.dto.PassengerDTO;
import com.railway.passengerservice.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/passengers")
@Tag(name = "Passenger Service", description = "Passenger Management API")
public class PassengerController {
//    private static final Logger log = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private PassengerService passengerService;

    @Operation(summary = "Add a new passenger")
    @PostMapping
    public ResponseEntity<PassengerDTO> addPassenger(@Valid @RequestBody PassengerDTO passengerDTO) {
        log.info("Adding new passenger: {}", passengerDTO.getEmail());
        PassengerDTO savedPassenger = passengerService.addPassenger(passengerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassenger);
    }

    @Operation(summary = "Get passenger by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@PathVariable Long id) {
        log.info("Fetching passenger details for ID: {}", id);
        PassengerDTO passengerDTO = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passengerDTO);
    }

    @Operation(summary = "Get all passengers")
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        log.info("Fetching all passengers");
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    @Operation(summary = "Update passenger details")
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerDTO passengerDTO) {
        log.info("Updating passenger with ID: {}", id);
        PassengerDTO updatedPassenger = passengerService.updatePassenger(id, passengerDTO);
        return ResponseEntity.ok(updatedPassenger);
    }

    @Operation(summary = "Delete passenger by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePassenger(@PathVariable Long id) {
        log.info("Deleting passenger with ID: {}", id);
        passengerService.deletePassenger(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Passenger deleted successfully."));
    }
}
