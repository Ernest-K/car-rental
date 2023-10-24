package com.project.carrental.service;

import com.project.carrental.dto.request.ReservationRequest;
import com.project.carrental.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse getReservationById(Long reservationId);
    List<ReservationResponse> getReservations();
    ReservationResponse createReservation(Long carId, ReservationRequest reservationRequest);
    ReservationResponse updateReservation(Long reservationId, ReservationRequest reservationRequest);
    void deleteReservation(Long reservationId);
}
