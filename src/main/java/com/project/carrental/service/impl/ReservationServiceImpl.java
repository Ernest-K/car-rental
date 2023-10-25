package com.project.carrental.service.impl;

import com.project.carrental.dto.request.ReservationRequest;
import com.project.carrental.dto.response.ReservationResponse;
import com.project.carrental.mapper.ReservationMapper;
import com.project.carrental.model.Car;
import com.project.carrental.model.Driver;
import com.project.carrental.model.Reservation;
import com.project.carrental.repository.CarRepository;
import com.project.carrental.repository.DriverRepository;
import com.project.carrental.repository.ReservationRepository;
import com.project.carrental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    @Override
    public ReservationResponse getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        return reservationMapper.reservationToReservationResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream().map(reservationMapper::reservationToReservationResponse).collect(Collectors.toList());
    }

    @Override
    public ReservationResponse createReservation(Long carId, ReservationRequest reservationRequest) {
        Car car = carRepository.findById(carId).get();

        Driver driver = Driver.builder()
                .firstName(reservationRequest.getFirstName())
                .lastName(reservationRequest.getLastName())
                .email(reservationRequest.getEmail())
                .phoneNumber(reservationRequest.getPhoneNumber())
                .build();

        driverRepository.save(driver);

        Reservation reservation = Reservation.builder()
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .driver(driver)
                .car(car)
                .build();

        reservationRepository.save(reservation);

        return reservationMapper.reservationToReservationResponse(reservation);
    }

    @Override
    public ReservationResponse updateReservation(Long reservationId, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        Driver driver = reservation.getDriver();

        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        driver.setFirstName(reservationRequest.getFirstName());
        driver.setLastName(reservationRequest.getLastName());
        driver.setEmail(reservationRequest.getEmail());
        driver.setPhoneNumber(reservationRequest.getPhoneNumber());
        reservation.setDriver(driver);

        reservationRepository.save(reservation);

        return reservationMapper.reservationToReservationResponse(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        reservationRepository.delete(reservation);
    }
}
