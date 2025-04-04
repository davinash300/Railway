package com.railway.bookingservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railway.bookingservice.client.PassengerServiceClient;
import com.railway.bookingservice.client.TrainServiceClient;
import com.railway.bookingservice.dto.BookingRequestDTO;
import com.railway.bookingservice.dto.BookingResponseDTO;
import com.railway.bookingservice.dto.PassengerDTO;
import com.railway.bookingservice.dto.TrainDTO;
import com.railway.bookingservice.entity.Booking;
import com.railway.bookingservice.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

    private final TrainServiceClient trainServiceClient;
    private final PassengerServiceClient passengerServiceClient;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(TrainServiceClient trainServiceClient,
                              PassengerServiceClient passengerServiceClient,
                              BookingRepository bookingRepository) {
        this.trainServiceClient = trainServiceClient;
        this.passengerServiceClient = passengerServiceClient;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    @Override
    public BookingResponseDTO bookTicket(BookingRequestDTO request) {
        Long trainId = request.getTrainId();
        Long passengerId = request.getPassengerId();
        int requestedSeats = request.getNumberOfSeats();

        // Fetch train details
        TrainDTO train = trainServiceClient.getTrainById(trainId);
        LocalDate journeyDate = train.getDepartureDate();
        String trainNumber = train.getTrainNumber();
        double seatFare = train.getFare();

        // Fetch passenger details
        PassengerDTO passenger = passengerServiceClient.getPassengerById(passengerId);

        // Calculate total fare
        double totalFare = requestedSeats * seatFare;

        // Check available seats
        int availableSeats = train.getAvailableSeats();

        if (availableSeats >= requestedSeats) {
            // Confirm booking
            trainServiceClient.updateSeats(trainNumber, journeyDate, requestedSeats);

            Booking booking = Booking.builder()
                    .trainId(trainId)
                    .passengerId(passengerId)
                    .numberOfSeats(requestedSeats)
                    .status("CONFIRMED")
                    .fare(totalFare)
                    .bookingTime(LocalDateTime.now())
                    .build();

            bookingRepository.save(booking);

            return BookingResponseDTO.builder()
                    .bookingId(booking.getId())
                    .status(booking.getStatus())
                    .totalFare(booking.getFare())
                    .passenger(passenger)
                    .build();
        } else {
            // Create waitlisted booking
            List<Booking> waitingBookings = bookingRepository.findByStatusStartingWith("WAITING");

            long waitlistCount = waitingBookings.stream()
                    .filter(b -> {
                        try {
                            TrainDTO bTrain = trainServiceClient.getTrainById(b.getTrainId());
                            return bTrain.getTrainNumber().equals(trainNumber) &&
                                   bTrain.getDepartureDate().isEqual(journeyDate);
                        } catch (Exception e) {
                            return false;
                        }
                    }).count();

            int waitingNumber = (int) waitlistCount + 1;

            Booking booking = Booking.builder()
                    .trainId(trainId)
                    .passengerId(passengerId)
                    .numberOfSeats(requestedSeats)
                    .status("WAITING - WL" + waitingNumber)
                    .fare(totalFare)
                    .bookingTime(LocalDateTime.now())
                    .build();

            bookingRepository.save(booking);

            return BookingResponseDTO.builder()
                    .bookingId(booking.getId())
                    .status(booking.getStatus())
                    .totalFare(booking.getFare())
                    .passenger(passenger)
                    .build();
        }
    }

    @Override
    public BookingResponseDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));

        PassengerDTO passenger = passengerServiceClient.getPassengerById(booking.getPassengerId());

        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .status(booking.getStatus())
                .totalFare(booking.getFare())
                .passenger(passenger)
                .build();
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> {
                    PassengerDTO passenger = passengerServiceClient.getPassengerById(booking.getPassengerId());
                    return BookingResponseDTO.builder()
                            .bookingId(booking.getId())
                            .status(booking.getStatus())
                            .totalFare(booking.getFare())
                            .passenger(passenger)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookingResponseDTO updateBooking(Long bookingId, BookingRequestDTO request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));

        TrainDTO train = trainServiceClient.getTrainById(request.getTrainId());

        if (train.getAvailableSeats() + booking.getNumberOfSeats() < request.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats available for update!");
        }

        trainServiceClient.updateSeats(
                train.getTrainNumber(),
                LocalDate.now(),
                request.getNumberOfSeats() - booking.getNumberOfSeats()
        );

        booking.setTrainId(train.getId());
        booking.setNumberOfSeats(request.getNumberOfSeats());
        booking.setFare(train.getFare() * request.getNumberOfSeats());
        bookingRepository.save(booking);

        PassengerDTO passenger = passengerServiceClient.getPassengerById(booking.getPassengerId());

        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .status(booking.getStatus())
                .totalFare(booking.getFare())
                .passenger(passenger)
                .build();
    }

    @Transactional
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));

        TrainDTO train = trainServiceClient.getTrainById(booking.getTrainId());
        trainServiceClient.updateSeats(train.getTrainNumber(), LocalDate.now(), -booking.getNumberOfSeats());

        bookingRepository.delete(booking);
    }

    @Override
    public List<BookingResponseDTO> getAllWaitingListBookings() {
        List<Booking> waitingBookings = bookingRepository.findAll().stream()
                .filter(booking -> booking.getStatus() != null && booking.getStatus().startsWith("WAITING"))
                .collect(Collectors.toList());

        return waitingBookings.stream()
                .map(booking -> {
                    PassengerDTO passenger = passengerServiceClient.getPassengerById(booking.getPassengerId());
                    return BookingResponseDTO.builder()
                            .bookingId(booking.getId())
                            .status(booking.getStatus())
                            .totalFare(booking.getFare())
                            .passenger(passenger)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
