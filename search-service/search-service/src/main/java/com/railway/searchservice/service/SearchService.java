package com.railway.searchservice.service;

import com.railway.searchservice.dto.TrainDTO;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    //List<TrainDTO> searchTrains(String source, String destination);
    TrainDTO searchByTrainNumber(String trainNumber);

    List<TrainDTO> searchTrains(String source, String destination, LocalDate date);
}
