package com.railway.trainservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railway.trainservice.dto.TrainDTO;
import com.railway.trainservice.entity.Train;
import com.railway.trainservice.exception.DuplicateTrainNumberException;
import com.railway.trainservice.exception.ResourceNotFoundException;
import com.railway.trainservice.repository.TrainRepository;

import jakarta.transaction.Transactional;

@Service
public class TrainServiceImpl implements TrainService {
    private static final Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TrainDTO> searchTrains(String source, String destination, LocalDate date) {
        log.info("Fetching trains from {} to {} on {}", source, destination, date);
        List<Train> trains = trainRepository.findBySourceAndDestinationAndDepartureDate(source, destination, date);
        return trains.stream()
                .map(train -> modelMapper.map(train, TrainDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainDTO> searchTrains(String source, String destination) {
        log.info("Fetching trains from {} to {}", source, destination);
        List<Train> trains = trainRepository.findBySourceAndDestination(source, destination);
        return trains.stream()
                .map(train -> modelMapper.map(train, TrainDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        log.info("Fetching all trains");
        return trainRepository.findAll().stream()
                .map(train -> modelMapper.map(train, TrainDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        log.info("Fetching train with ID: {}", id);
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + id));
        return modelMapper.map(train, TrainDTO.class);
    }

    @Override
    public TrainDTO getTrainByNumber(String trainNumber) {
        log.info("Fetching train with number: {}", trainNumber);
        Train train = trainRepository.findByTrainNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));
        return modelMapper.map(train, TrainDTO.class);
    }

    @Override
    @Transactional
    public TrainDTO createTrain(TrainDTO trainDTO) {
        log.info("Creating train: {}", trainDTO.getName());

        // Check if train with same number already exists
        if (trainRepository.findByTrainNumber(trainDTO.getTrainNumber()).isPresent()) {
            throw new DuplicateTrainNumberException("Train number " + trainDTO.getTrainNumber() + " already exists.");
        }

        Train train = modelMapper.map(trainDTO, Train.class);
        Train savedTrain = trainRepository.save(train);
        return modelMapper.map(savedTrain, TrainDTO.class);
    }


    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        log.info("Updating train with ID: {}", id);
        Train existingTrain = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + id));
        modelMapper.map(trainDTO, existingTrain);
        return modelMapper.map(trainRepository.save(existingTrain), TrainDTO.class);
    }

    @Override
    public void deleteTrain(Long id) {
        log.info("Deleting train with ID: {}", id);
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with ID: " + id));
        trainRepository.delete(train);
    }

    @Override
    @Transactional
    public boolean updateAvailableSeats(String trainNumber, LocalDate date, int bookedSeats) {
        log.info("Updating available seats for train {} on {} with booked seats: {}", trainNumber, date, bookedSeats);
        Train train = trainRepository.findByTrainNumberAndDepartureDate(trainNumber, date)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber + " on date: " + date));

        if (train.getAvailableSeats() >= bookedSeats) {
            train.setAvailableSeats(train.getAvailableSeats() - bookedSeats);
            trainRepository.save(train);
            return true;
        } else {
            log.warn("Not enough available seats for train {} on {}", trainNumber, date);
            return false;
        }
    }

    @Override
    public Integer getAvailableSeats(String trainNumber, LocalDate date) {
        log.info("Fetching available seats for train {} on {}", trainNumber, date);
        Train train = trainRepository.findByTrainNumberAndDepartureDate(trainNumber, date)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber + " on date: " + date));
        return train.getAvailableSeats();
    }
}
