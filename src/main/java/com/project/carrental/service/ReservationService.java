package com.project.carrental.service;

import com.project.carrental.dto.request.ReservationRequest;
import com.project.carrental.dto.request.UpdateReservationRequest;
import com.project.carrental.dto.response.ReservationResponse;
import com.project.carrental.model.Price;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    ReservationResponse getReservationById(Long reservationId);
    List<ReservationResponse> getReservations();
    ReservationResponse createReservation(Long carId, ReservationRequest reservationRequest);
    ReservationResponse updateReservation(Long reservationId, UpdateReservationRequest updateReservationRequest);
    void deleteReservation(Long reservationId);
    BigDecimal calculateCost(Price price, LocalDate startDate, LocalDate endDate);
}
