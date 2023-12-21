package com.project.carrental.service;

import com.project.carrental.dto.request.CarRequest;
import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.mapper.CarMapper;
import com.project.carrental.model.Car;
import com.project.carrental.model.Category;
import com.project.carrental.model.Price;
import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import com.project.carrental.repository.CarRepository;
import com.project.carrental.repository.CategoryRepository;
import com.project.carrental.service.impl.CarServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void getCarDetailByIdShouldReturnCarDetail() {
        Long carId = 1L;
        Car car = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(carMapper.carToCarDetailResponse(any(Car.class))).thenReturn(new CarDetailResponse());

        CarDetailResponse carDetailResponse = carService.getCarDetailById(carId);

        assertThat(carDetailResponse).isNotNull();
        verify(carRepository, times(1)).findById(carId);
        verify(carMapper, times(1)).carToCarDetailResponse(car);
    }

    @Test
    void getCarDetailByIdShouldThrowEntityNotFoundException() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            CarDetailResponse carDetailResponse = carService.getCarDetailById(carId);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(carRepository, times(1)).findById(carId);
        verifyNoInteractions(carMapper);
    }

    @Test
    void getCarsShouldReturnCarResponses() {
        List<Car> cars = List.of(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(cars);
        when(carMapper.carToCarResponse(any(Car.class))).thenReturn(new CarResponse());

        List<CarResponse> carResponses = carService.getCars();

        assertThat(carResponses).isNotEmpty();
        assertThat(carResponses.size()).isEqualTo(cars.size());
        verify(carRepository, times(1)).findAll();
        verify(carMapper, times(cars.size())).carToCarResponse(any(Car.class));
    }

    @Test
    void getCarsByCategoryShouldReturnCarResponses() {
        List<Car> cars = List.of(new Car(), new Car());
        String categoryName = "SUV";
        when(carRepository.findByCategoryName(categoryName)).thenReturn(cars);
        when(carMapper.carToCarResponse(any(Car.class))).thenReturn(new CarResponse());

        List<CarResponse> carResponses = carService.getCarsByCategory(categoryName);

        assertThat(carResponses).isNotEmpty();
        assertThat(carResponses.size()).isEqualTo(cars.size());
        verify(carRepository, times(1)).findByCategoryName(categoryName);
        verify(carMapper, times(cars.size())).carToCarResponse(any(Car.class));
    }

    @Test
    void createCarShouldReturnCarDetailResponse() {
        CarRequest carRequest = createSampleCarRequest();
        Category category = new Category();
        when(categoryRepository.findById(carRequest.getCategoryId())).thenReturn(Optional.of(category));
        when(carRepository.save(any())).thenReturn(new Car());
        when(carMapper.carToCarDetailResponse(any(Car.class))).thenReturn(new CarDetailResponse());

        CarDetailResponse carDetailResponse = carService.createCar(carRequest);

        assertThat(carDetailResponse).isNotNull();
        verify(categoryRepository, times(1)).findById(carRequest.getCategoryId());
        verify(carRepository, times(1)).save(any(Car.class));
        verify(carMapper, times(1)).carToCarDetailResponse(any(Car.class));
    }

    @Test
    void createCarShouldThrowEntityNotFoundExceptionWhenWrongCategoryId() {
        CarRequest carRequest = createSampleCarRequest();
        when(categoryRepository.findById(carRequest.getCategoryId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            CarDetailResponse carDetailResponse = carService.createCar(carRequest);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(categoryRepository, times(1)).findById(carRequest.getCategoryId());
        verifyNoInteractions(carRepository);
        verifyNoInteractions(carMapper);
    }

    @Test
    void updateCarShouldReturnCarDetailResponse() {
        Long carId = 1L;
        CarRequest carRequest = createSampleCarRequest();
        Car car = createSampleCar();
        Category category = new Category();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(categoryRepository.findById(carRequest.getCategoryId())).thenReturn(Optional.of(category));
        when(carRepository.save(any())).thenReturn(new Car());
        when(carMapper.carToCarDetailResponse(any())).thenReturn(new CarDetailResponse());

        CarDetailResponse carDetailResponse = carService.updateCar(carId, carRequest);

        assertThat(carDetailResponse).isNotNull();
        verify(carRepository, times(1)).findById(carId);
        verify(categoryRepository, times(1)).findById(carRequest.getCategoryId());
        verify(carRepository, times(1)).save(any(Car.class));
        verify(carMapper, times(1)).carToCarDetailResponse(any(Car.class));
    }

    @Test
    void updateCarShouldThrowEntityNotFoundExceptionWhenWrongCarId() {
        Long carId = 1L;
        CarRequest carRequest = createSampleCarRequest();
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            CarDetailResponse carDetailResponse = carService.updateCar(carId, carRequest);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(carRepository, times(1)).findById(carId);
        verifyNoInteractions(categoryRepository);
        verify(carRepository, never()).save(any(Car.class));
        verifyNoInteractions(carMapper);
    }

    @Test
    void updateCarShouldThrowEntityNotFoundExceptionWhenWrongCategoryId() {
        Long carId = 1L;
        CarRequest carRequest = createSampleCarRequest();
        Car car = createSampleCar();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(categoryRepository.findById(carRequest.getCategoryId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            CarDetailResponse carDetailResponse = carService.updateCar(carId, carRequest);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(carRepository, times(1)).findById(carId);
        verify(categoryRepository, times(1)).findById(carId);
        verify(carRepository, never()).save(any(Car.class));
        verifyNoInteractions(carMapper);
    }

    @Test
    void deleteCarShouldDeleteCar() {
        Long carId = 1L;
        Car car = createSampleCar();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        assertThatNoException().isThrownBy(() -> {
            carService.deleteCar(carId);
        });

        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, times(1)).delete(car);
    }

    @Test
    void deleteCarShouldThrowEntityNotFoundExceptionWhenWrongCarId() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            carService.deleteCar(carId);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(carRepository, times(1)).findById(carId);
        verify(carRepository, never()).delete(any(Car.class));
    }

    private CarRequest createSampleCarRequest(){
        return new CarRequest("AVAILABLE", "BMW", "M3", 2006, 343, "PETROL", "MANUAL", "REAR", BigDecimal.valueOf(400.00), BigDecimal.valueOf(300.00), BigDecimal.valueOf(200.00), 1L);
    }

    private Car createSampleCar() {
        Price price = Price.builder()
                .forDay(BigDecimal.valueOf(400.00))
                .forTwoToFourDays(BigDecimal.valueOf(300.00))
                .forWeek(BigDecimal.valueOf(100.00)).build();

        Category category = Category.builder().name("coupe").build();

        return Car.builder()
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
    }

}