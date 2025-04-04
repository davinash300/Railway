package com.railway.trainservice.service;

import com.railway.trainservice.dto.SeatAvailabilityDTO;
import com.railway.trainservice.entity.SeatAvailability;
import com.railway.trainservice.repository.SeatAvailabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatAvailabilityServiceImpl implements SeatAvailabilityService {

    private static final Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;

    @Override
    public SeatAvailability saveSeatAvailability(SeatAvailabilityDTO seatAvailabilityDTO) {
        SeatAvailability seatAvailability = new SeatAvailability();
        seatAvailability.setTrainNumber(seatAvailabilityDTO.getTrainNumber());
        seatAvailability.setTravelDate(seatAvailabilityDTO.getTravelDate());
        seatAvailability.setAvailableSeats(seatAvailabilityDTO.getAvailableSeats());

        log.info("Saving seat availability: {}", seatAvailability);
        return seatAvailabilityRepository.save(seatAvailability);
    }
}

