package com.railway.searchservice.service;

import com.railway.searchservice.client.TrainServiceClient;
import com.railway.searchservice.dto.TrainDTO;
import com.railway.searchservice.exception.TrainNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private TrainServiceClient trainServiceClient;

    public List<TrainDTO> searchTrains(String source, String destination, LocalDate date) {
        return trainServiceClient.searchTrains(source, destination, date);
    }

//    @Override
//    public List<TrainDTO> searchTrains(String source, String destination) {
//        log.info("Searching trains from {} to {}", source, destination);
//        return trainServiceClient.searchTrains(source, destination);
//    }

    @Override
    public TrainDTO searchByTrainNumber(String trainNumber) {
        log.info("Fetching train details for train number: {}", trainNumber);
        try {
            return trainServiceClient.getTrainByNumber(trainNumber);
        } catch (TrainNotFoundException e) {
            log.error("Train not found: {}", trainNumber);
            throw new TrainNotFoundException(trainNumber);
        }
    }


}