package com.railway.passengerservice.service;

import com.railway.passengerservice.dto.PassengerDTO;

import java.util.List;

public interface PassengerService {
    PassengerDTO addPassenger(PassengerDTO passengerDTO);
    PassengerDTO getPassengerById(Long id);
    List<PassengerDTO> getAllPassengers();
    PassengerDTO updatePassenger(Long id, PassengerDTO passengerDTO);
    void deletePassenger(Long id);
}
