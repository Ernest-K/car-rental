package com.project.carrental.controller;

import com.project.carrental.dto.request.CarRequest;
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

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id){
        return new ResponseEntity<>(carService.getCarById(id), HttpStatus.OK);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarResponse>> getCars(){
        return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
    }

    @GetMapping(value = "/cars", params = "categoryName")
    public ResponseEntity<List<CarResponse>> getCarsByCategory(@RequestParam String categoryName){
        return new ResponseEntity<>(carService.getCarsByCategory(categoryName), HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarRequest carRequest){
        return new ResponseEntity<>(carService.createCar(carRequest), HttpStatus.CREATED);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody @Valid CarRequest carRequest){
        return new ResponseEntity<>(carService.updateCar(id, carRequest), HttpStatus.OK);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
        return new ResponseEntity<>("Car id: " + id + " deleted successfully", HttpStatus.OK);
    }

}
