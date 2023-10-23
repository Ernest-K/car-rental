package com.project.carrental.dto.response;

import com.project.carrental.model.Car;
import com.project.carrental.model.Driver;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationResponse {
    private Long id;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private Driver driver;
}
