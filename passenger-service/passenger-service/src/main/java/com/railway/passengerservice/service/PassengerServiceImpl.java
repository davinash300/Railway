package com.railway.passengerservice.service;

import com.railway.passengerservice.dto.PassengerDTO;
import com.railway.passengerservice.entity.Passenger;
import com.railway.passengerservice.exception.ResourceNotFoundException;
import com.railway.passengerservice.repository.PassengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService {
//    private static final Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PassengerDTO addPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = modelMapper.map(passengerDTO, Passenger.class);
        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info("Passenger added with ID: {}", savedPassenger.getId());
        return modelMapper.map(savedPassenger, PassengerDTO.class);
    }

    @Override
    public PassengerDTO getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with ID: " + id));
        return modelMapper.map(passenger, PassengerDTO.class);
    }

    @Override
    public List<PassengerDTO> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengers.stream()
                .map(passenger -> modelMapper.map(passenger, PassengerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PassengerDTO updatePassenger(Long id, PassengerDTO passengerDTO) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with ID: " + id));

        modelMapper.map(passengerDTO, passenger);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        log.info("Passenger updated with ID: {}", id);
        return modelMapper.map(updatedPassenger, PassengerDTO.class);
    }

    @Override
    public void deletePassenger(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with ID: " + id));

        passengerRepository.delete(passenger);
        log.info("Passenger deleted with ID: {}", id);
    }
}
