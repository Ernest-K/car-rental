package com.project.carrental.controller;

import com.project.carrental.dto.request.ReservationRequest;
import com.project.carrental.dto.response.ReservationResponse;
import com.project.carrental.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long reservationId){
        return new ResponseEntity<>(reservationService.getReservationById(reservationId), HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations(){
        return new ResponseEntity<>(reservationService.getReservations(), HttpStatus.OK);
    }

    @PostMapping("/cars/{carId}/reservation")
    public ResponseEntity<ReservationResponse> createReservation(@PathVariable Long carId, @RequestBody @Valid ReservationRequest reservationRequest){
        return new ResponseEntity<>(reservationService.createReservation(carId, reservationRequest), HttpStatus.OK);
    }

    @PutMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long reservationId, @RequestBody @Valid ReservationRequest reservationRequest){
        return new ResponseEntity<>(reservationService.updateReservation(reservationId, reservationRequest), HttpStatus.OK);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId){
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>("Reservation id: " + reservationId + " deleted successfully", HttpStatus.OK);
    }
}
