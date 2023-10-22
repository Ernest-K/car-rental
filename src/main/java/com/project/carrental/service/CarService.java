package com.project.carrental.service;

import com.project.carrental.dto.request.CarRequest;
import com.project.carrental.dto.response.CarResponse;

import java.util.List;

public interface CarService {
    CarResponse getCarById(Long carId);
    List<CarResponse> getCars();
    List<CarResponse> getCarsByCategory(String categoryName);
    CarResponse createCar(CarRequest carRequest);
}
