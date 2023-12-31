package com.project.carrental.service.impl;

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
import com.project.carrental.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CarMapper carMapper;

    @Override
    public CarDetailResponse getCarDetailById(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car id: " + carId + " not found"));

        return carMapper.carToCarDetailResponse(car);
    }

    @Override
    public List<CarResponse> getCars() {
        List<Car> cars = carRepository.findAllWithCategoryAndPrice();

        return cars.stream().map(carMapper::carToCarResponse).collect(Collectors.toList());
    }

    @Override
    public List<CarResponse> getCarsByCategory(String categoryName) {
        List<Car> cars = carRepository.findByCategoryName(categoryName);
        return cars.stream().map(carMapper::carToCarResponse).collect(Collectors.toList());
    }

    @Override
    public CarDetailResponse createCar(CarRequest carRequest) {
        Category category = categoryRepository.findById(carRequest.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category id: " + carRequest.getCategoryId() + " not found"));

        Price price = Price.builder()
                .forDay(carRequest.getPriceForDay())
                .forTwoToFourDays(carRequest.getPriceForTwoToFourDays())
                .forWeek(carRequest.getPriceForWeek())
                .build();

//        priceRepository.save(price);

        Car car = Car.builder()
                .status(Status.valueOf(carRequest.getStatus().toUpperCase()))
                .make(carRequest.getMake())
                .model(carRequest.getModel())
                .productionYear(carRequest.getProductionYear())
                .power(carRequest.getPower())
                .fuelType(FuelType.valueOf(carRequest.getFuelType().toUpperCase()))
                .transmissionType(TransmissionType.valueOf(carRequest.getTransmissionType().toUpperCase()))
                .driveType(DriveType.valueOf(carRequest.getDriveType().toUpperCase()))
                .price(price)
                .category(category)
                .build();

        carRepository.save(car);

        return carMapper.carToCarDetailResponse(car);
    }

    @Override
    public CarDetailResponse updateCar(Long carId, CarRequest carRequest) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car id: " + carId + " not found"));
        Category category = categoryRepository.findById(carRequest.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category id: " + carRequest.getCategoryId() + " not found"));
        Price price = car.getPrice();

        car.setStatus(Status.valueOf(carRequest.getStatus().toUpperCase()));
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setProductionYear(carRequest.getProductionYear());
        car.setPower(carRequest.getPower());
        car.setFuelType(FuelType.valueOf(carRequest.getFuelType().toUpperCase()));
        car.setTransmissionType(TransmissionType.valueOf(carRequest.getTransmissionType().toUpperCase()));
        car.setDriveType(DriveType.valueOf(carRequest.getDriveType().toUpperCase()));
        price.setForDay(carRequest.getPriceForDay());
        price.setForTwoToFourDays(carRequest.getPriceForTwoToFourDays());
        price.setForWeek(carRequest.getPriceForWeek());
        car.setCategory(category);

        carRepository.save(car);

        return carMapper.carToCarDetailResponse(car);
    }

    @Override
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car id: " + carId + " not found"));

        carRepository.delete(car);
    }
}
