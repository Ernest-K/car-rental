package com.project.carrental.service.impl;

import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.mapper.CarMapper;
import com.project.carrental.model.Car;
import com.project.carrental.repository.CarRepository;
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
}
