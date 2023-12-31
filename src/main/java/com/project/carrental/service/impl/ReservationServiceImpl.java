package com.project.carrental.service.impl;

import com.project.carrental.dto.request.ReservationRequest;
import com.project.carrental.dto.request.UpdateReservationRequest;
import com.project.carrental.dto.response.ReservationResponse;
import com.project.carrental.mapper.ReservationMapper;
import com.project.carrental.model.Car;
import com.project.carrental.model.Driver;
import com.project.carrental.model.Price;
import com.project.carrental.model.Reservation;
import com.project.carrental.repository.CarRepository;
import com.project.carrental.repository.ReservationRepository;
import com.project.carrental.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CarRepository carRepository;

    @Override
    public ReservationResponse getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation id: " + reservationId + " not found"));

        return reservationMapper.reservationToReservationResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAllWithDriverAndCar();
        return reservations.stream().map(reservationMapper::reservationToReservationResponse).collect(Collectors.toList());
    }

    @Override
    public ReservationResponse createReservation(Long carId, ReservationRequest reservationRequest) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car id: " + carId + " not found"));

        Driver driver = Driver.builder()
                .firstName(reservationRequest.getFirstName())
                .lastName(reservationRequest.getLastName())
                .email(reservationRequest.getEmail())
                .phoneNumber(reservationRequest.getPhoneNumber())
                .build();

        Reservation reservation = Reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .driver(driver)
                .car(car)
                .cost(calculateCost(car.getPrice(), reservationRequest.getStartDate(), reservationRequest.getEndDate()))
                .build();

        reservationRepository.save(reservation);

        return reservationMapper.reservationToReservationResponse(reservation);
    }

    @Override
    public ReservationResponse updateReservation(Long reservationId, UpdateReservationRequest updateReservationRequest) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation id: " + reservationId + " not found"));
        Driver driver = reservation.getDriver();

        reservation.setStartDate(updateReservationRequest.getStartDate());
        reservation.setEndDate(updateReservationRequest.getEndDate());
        driver.setFirstName(updateReservationRequest.getFirstName());
        driver.setLastName(updateReservationRequest.getLastName());
        driver.setEmail(updateReservationRequest.getEmail());
        driver.setPhoneNumber(updateReservationRequest.getPhoneNumber());
        reservation.setCost(updateReservationRequest.getCost());
        reservation.setDriver(driver);

        reservationRepository.save(reservation);

        return reservationMapper.reservationToReservationResponse(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation id: " + reservationId + " not found"));

        reservationRepository.delete(reservation);
    }

    @Override
    public BigDecimal calculateCost(Price price, LocalDate startDate, LocalDate endDate) {
        int numberOfDays = startDate.equals(endDate) ? 1 : (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        switch (numberOfDays){
            case 1:
                return price.getForDay();
            case 2, 3, 4:
                return price.getForTwoToFourDays().multiply(BigDecimal.valueOf(numberOfDays));
            default:
                return price.getForWeek().multiply(BigDecimal.valueOf(numberOfDays));
        }
    }
}
