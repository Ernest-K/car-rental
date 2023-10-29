package com.project.carrental.controller;

import com.project.carrental.dto.request.CarRequest;
import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    @GetMapping("/cars/{carId}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<CarDetailResponse> getCarDetailById(@PathVariable Long carId){
        return new ResponseEntity<>(carService.getCarDetailById(carId), HttpStatus.OK);
    }

    @GetMapping("/cars")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<CarResponse>> getCars(){
        return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
    }

    @GetMapping(value = "/cars", params = "categoryName")
    public ResponseEntity<List<CarResponse>> getCarsByCategory(@RequestParam String categoryName){
        return new ResponseEntity<>(carService.getCarsByCategory(categoryName), HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<CarDetailResponse> createCar(@RequestBody @Valid CarRequest carRequest){
        return new ResponseEntity<>(carService.createCar(carRequest), HttpStatus.CREATED);
    }

    @PutMapping("/cars/{carId}")
    public ResponseEntity<CarDetailResponse> updateCar(@PathVariable Long carId, @RequestBody @Valid CarRequest carRequest){
        return new ResponseEntity<>(carService.updateCar(carId, carRequest), HttpStatus.OK);
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId){
        carService.deleteCar(carId);
        return new ResponseEntity<>("Car id: " + carId + " deleted successfully", HttpStatus.OK);
    }

}
