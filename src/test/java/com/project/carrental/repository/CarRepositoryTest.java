package com.project.carrental.repository;

import com.project.carrental.model.Car;
import com.project.carrental.model.Category;
import com.project.carrental.model.Price;
import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();

        Price price = Price.builder()
                .forDay(BigDecimal.valueOf(400.00))
                .forTwoToFourDays(BigDecimal.valueOf(300.00))
                .forWeek(BigDecimal.valueOf(100.00)).build();

        Category category = Category.builder().name("coupe").build();

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
        testEntityManager.persist(car);
    }

    @Test
    void shouldFindAllCars() {
        List<Car> foundCarList = carRepository.findAll();

        assertThat(foundCarList).isNotEmpty();
        assertThat(foundCarList.size()).isEqualTo(1);
    }

    @Test
    void shouldFindCarByCategoryName() {
        List<Car> foundCarList = carRepository.findByCategoryName("coupe");

        assertThat(foundCarList).isNotEmpty();
        assertThat(foundCarList.get(0).getMake()).isEqualTo("BMW");
    }

    @Test
    void shouldNotFindCarByCategoryName() {
        List<Car> foundCarList = carRepository.findByCategoryName("cabrio");

        assertThat(foundCarList).isEmpty();
    }
}