package com.project.carrental.dto.response;

import com.project.carrental.model.Driver;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationResponse {
    private Long id;
    private CarResponse car;
    private LocalDate startDate;
    private LocalDate endDate;
    private Driver driver;
    private BigDecimal cost;
}
