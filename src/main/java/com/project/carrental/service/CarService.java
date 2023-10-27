package com.project.carrental.service;

import com.project.carrental.dto.request.CarRequest;
import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.dto.response.CarResponse;

import java.util.List;

public interface CarService {
    CarDetailResponse getCarDetailById(Long carId);
    List<CarResponse> getCars();
    List<CarResponse> getCarsByCategory(String categoryName);
    CarDetailResponse createCar(CarRequest carRequest);
    CarDetailResponse updateCar(Long carId, CarRequest carRequest);
    void deleteCar(Long carId);
}
