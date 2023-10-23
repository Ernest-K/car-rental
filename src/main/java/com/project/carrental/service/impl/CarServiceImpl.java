package com.project.carrental.service.impl;

import com.project.carrental.dto.request.CarRequest;
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
import com.project.carrental.repository.PriceRepository;
import com.project.carrental.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final PriceRepository priceRepository;
    private final CarMapper carMapper;

    @Override
    public CarResponse getCarById(Long carId) {
        Optional<Car> car = carRepository.findById(carId);
        car.orElseThrow(() -> new EntityNotFoundException("Car not found"));
        return carMapper.carToCarResponse(car.get());
    }

    @Override
    public List<CarResponse> getCars() {
        List<Car> cars = carRepository.findAll();

        return cars.stream().map(carMapper::carToCarResponse).collect(Collectors.toList());
    }

    @Override
    public List<CarResponse> getCarsByCategory(String categoryName) {
        List<Car> cars = carRepository.findByCategoryName(categoryName);
        return cars.stream().map(carMapper::carToCarResponse).collect(Collectors.toList());
    }

    @Override
    public CarResponse createCar(CarRequest carRequest) {
        Category category = categoryRepository.findById(carRequest.getCategoryId()).get();

        Price price = Price.builder()
                .perDay(carRequest.getPricePerDay())
                .twoToFourDays(carRequest.getPriceForTwoToFourDays())
                .perWeek(carRequest.getPricePerWeek())
                .build();

        priceRepository.save(price);

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

        return carMapper.carToCarResponse(car);
    }

    @Override
    public CarResponse updateCar(Long carId, CarRequest carRequest) {
        Car car = carRepository.findById(carId).get();
        Category category = categoryRepository.findById(carRequest.getCategoryId()).get();
        Price price = car.getPrice();

        car.setStatus(Status.valueOf(carRequest.getStatus().toUpperCase()));
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setProductionYear(carRequest.getProductionYear());
        car.setPower(carRequest.getPower());
        car.setFuelType(FuelType.valueOf(carRequest.getFuelType().toUpperCase()));
        car.setTransmissionType(TransmissionType.valueOf(carRequest.getTransmissionType().toUpperCase()));
        car.setDriveType(DriveType.valueOf(carRequest.getDriveType().toUpperCase()));
        price.setPerDay(carRequest.getPricePerDay());
        price.setTwoToFourDays(carRequest.getPriceForTwoToFourDays());
        price.setPerWeek(carRequest.getPricePerWeek());
        car.setCategory(category);

        carRepository.save(car);

        return carMapper.carToCarResponse(car);
    }

    @Override
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId).get();

        carRepository.delete(car);

    }
}
