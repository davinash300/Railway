package com.railway.trainservice.service;

import java.time.LocalDate;
import java.util.List;

import com.railway.trainservice.dto.TrainDTO;

public interface TrainService {
    List<TrainDTO> searchTrains(String source, String destination, LocalDate date);
    List<TrainDTO> searchTrains(String source, String destination);
    List<TrainDTO> getAllTrains();
    TrainDTO getTrainById(Long id);
    TrainDTO getTrainByNumber(String trainNumber);
    TrainDTO createTrain(TrainDTO trainDTO);
    TrainDTO updateTrain(Long id, TrainDTO trainDTO);
    void deleteTrain(Long id);
    boolean updateAvailableSeats(String trainNumber, LocalDate date, int bookedSeats);
	Object getAvailableSeats(String trainNumber, LocalDate date);
}