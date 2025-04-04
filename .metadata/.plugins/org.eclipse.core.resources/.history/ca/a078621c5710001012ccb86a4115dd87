package com.railway.trainservice.service;

import com.railway.trainservice.dto.SeatAvailabilityDTO;
import com.railway.trainservice.dto.TrainDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {

    List<TrainDTO> searchTrains(String source, String destination, LocalDate date);

    List<TrainDTO> searchTrains(String source, String destination);
    SeatAvailabilityDTO getSeatAvailability(String trainNumber, LocalDate date);
    List<TrainDTO> getAllTrains();
    TrainDTO getTrainById(Long id);
    TrainDTO getTrainByNumber(String trainNumber);
    TrainDTO createTrain(TrainDTO trainDTO);
    TrainDTO updateTrain(Long id, TrainDTO trainDTO);
    void deleteTrain(Long id);

}
