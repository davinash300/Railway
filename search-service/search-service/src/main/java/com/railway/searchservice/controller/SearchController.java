package com.railway.searchservice.controller;

import com.railway.searchservice.dto.TrainDTO;
import com.railway.searchservice.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/search")
@Tag(name = "Search Service", description = "Train Search API")
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

//    @Operation(summary = "Search trains by source and destination")
//    @GetMapping
//    public ResponseEntity<List<TrainDTO>> searchTrains(@RequestParam String source, @RequestParam String destination) {
//        log.info("Received search request for trains from {} to {}", source, destination);
//        return ResponseEntity.ok(searchService.searchTrains(source, destination));
//    }

    @Operation(summary = "Search train by train number")
    @GetMapping("/number/{trainNumber}")
    public ResponseEntity<TrainDTO> searchByTrainNumber(@PathVariable String trainNumber) {
        log.info("Received request to search train with train number: {}", trainNumber);
        return ResponseEntity.ok(searchService.searchByTrainNumber(trainNumber));
    }

    @Operation(summary = "Search trains by source, destination, and date")
    @GetMapping
    public ResponseEntity<List<TrainDTO>> searchTrains(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        log.info("Received search request for trains from {} to {} on {}", source, destination, date);
        return ResponseEntity.ok((List<TrainDTO>) searchService.searchTrains(source, destination, date));
    }

}