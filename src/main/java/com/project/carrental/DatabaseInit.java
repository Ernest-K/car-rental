package com.project.carrental;

import com.project.carrental.model.*;
import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import com.project.carrental.repository.*;
import com.project.carrental.service.ReservationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class DatabaseInit {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @PostConstruct
    private void init(){

        Price price = Price.builder()
                .forDay(BigDecimal.valueOf(400.00))
                .forTwoToFourDays(BigDecimal.valueOf(300.00))
                .forWeek(BigDecimal.valueOf(100.00)).build();

        Category category = Category.builder()
                .name("Cabriolet").build();

        Car car = Car.builder()
                .status(Status.AVAILABLE)
                .make("BMW")
                .model("series 3")
                .productionYear(2006)
                .power(343)
                .fuelType(FuelType.PETROL)
                .transmissionType(TransmissionType.MANUAL)
                .driveType(DriveType.REAR)
                .price(price)
                .category(category).build();
        carRepository.save(car);

        Driver driver = Driver.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .phoneNumber("123456789")
                .build();

        Reservation reservation = Reservation.builder()
                .car(car)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .driver(driver)
                .cost(reservationService.calculateCost(price, LocalDate.now(), LocalDate.now().plusDays(3)))
                .build();

        reservationRepository.save(reservation);
    }
}
