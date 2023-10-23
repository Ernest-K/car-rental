package com.project.carrental;

import com.project.carrental.model.*;
import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import com.project.carrental.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class DatabaseInit {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final DriverRepository driverRepository;
    private final PriceRepository priceRepository;
    private final ReservationRepository reservationRepository;

    @PostConstruct
    private void init(){

        Price price = Price.builder()
                .perDay(400.0)
                .twoToFourDays(300.0)
                .perWeek(100.0).build();
        priceRepository.save(price);

        Category category = Category.builder()
                .name("sport").build();
        categoryRepository.save(category);

        Car car = Car.builder()
                .status(Status.AVAILABLE)
                .make("BMW")
                .model("series 3")
                .productionYear(2001)
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

        driverRepository.save(driver);

        Reservation reservation = Reservation.builder()
                .car(car)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .driver(driver)
                .build();

        reservationRepository.save(reservation);

    }

}
