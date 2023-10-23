package com.project.carrental.mapper;

import com.project.carrental.dto.response.ReservationResponse;
import com.project.carrental.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationResponse reservationToReservationResponse(Reservation reservation);
}
