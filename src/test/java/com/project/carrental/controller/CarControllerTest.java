package com.project.carrental.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.carrental.dto.request.CarRequest;
import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.model.Category;
import com.project.carrental.model.Price;
import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import com.project.carrental.service.impl.CarServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @MockBean
    private CarServiceImpl carService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCarDetailByIdShouldReturnCarDetailResponse() throws Exception {
        Long carId = 1L;
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.getCarDetailById(carId)).thenReturn(carDetailResponse);

        mockMvc.perform(get("/api/cars/{carId}", carId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value(carDetailResponse.getMake()));
    }

    @Test
    void getCarDetailByIdShouldReturnNotFound() throws Exception {
        Long carId = 1L;
        when(carService.getCarDetailById(carId)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/cars/{carId}", carId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getCarsShouldReturnCarResponses() throws Exception {
        when(carService.getCars()).thenReturn(List.of(createSampleCarResponse()));

        mockMvc.perform(get("/api/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getCarsByCategoryShouldReturnCarResponses() throws Exception {
        String categoryName = "coupe";
        when(carService.getCarsByCategory(categoryName)).thenReturn(List.of(createSampleCarResponse()));

        mockMvc.perform(get("/api/cars").param("categoryName", categoryName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void createCarShouldReturnCarDetailResponse() throws Exception {
        CarRequest carRequest = createSampleCarRequest();
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.createCar(carRequest)).thenReturn(carDetailResponse);

        mockMvc.perform(post("/api/cars")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value(carDetailResponse.getMake()));
    }

    @Test
    void createCarShouldReturnBadRequest() throws Exception {
        CarRequest carRequest = new CarRequest();
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.createCar(carRequest)).thenReturn(carDetailResponse);

        mockMvc.perform(post("/api/cars")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createCarShouldReturnUnauthorized() throws Exception {
        CarRequest carRequest = createSampleCarRequest();
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.createCar(carRequest)).thenReturn(carDetailResponse);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void updateCarShouldReturnCarDetailResponse() throws Exception {
        Long carId = 1L;
        CarRequest carRequest = createSampleCarRequest();
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.updateCar(carId, carRequest)).thenReturn(carDetailResponse);

        mockMvc.perform(put("/api/cars/{carId}", carId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make").value(carDetailResponse.getMake()));
    }

    @Test
    void updateCarShouldReturnBadRequest() throws Exception {
        Long carId = 1L;
        CarRequest carRequest = new CarRequest();
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.updateCar(carId, carRequest)).thenReturn(carDetailResponse);

        mockMvc.perform(put("/api/cars/{carId}", carId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCarShouldReturnUnauthorized() throws Exception {
        Long carId = 1L;
        CarRequest carRequest = createSampleCarRequest();
        CarDetailResponse carDetailResponse = createSampleCarDetailResponse();
        when(carService.updateCar(carId, carRequest)).thenReturn(carDetailResponse);

        mockMvc.perform(put("/api/cars/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void deleteCarShouldDeleteCar() throws Exception {
        Long carId = 1L;
        String successMessage = "Car id: " + carId + " deleted successfully";

        mockMvc.perform(delete("/api/cars/{carId}", carId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(successMessage));

        verify(carService, times(1)).deleteCar(carId);
    }

    @Test
    void deleteCarShouldReturnUnauthorized() throws Exception {
        Long carId = 1L;

        mockMvc.perform(delete("/api/cars/{carId}", carId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verifyNoInteractions(carService);
    }

    private CarRequest createSampleCarRequest() {
        return new CarRequest(
                Status.AVAILABLE.name(),
                "BMW",
                "M3",
                2006,
                343,
                FuelType.PETROL.name(),
                TransmissionType.MANUAL.name(),
                DriveType.REAR.name(),
                BigDecimal.valueOf(400.00),
                BigDecimal.valueOf(300.00),
                BigDecimal.valueOf(200.00),
                1L);
    }

    private CarResponse createSampleCarResponse() {
        Category category = Category.builder().name("coupe").build();
        Price price = Price.builder()
                .forDay(BigDecimal.valueOf(400.00))
                .forTwoToFourDays(BigDecimal.valueOf(300.00))
                .forWeek(BigDecimal.valueOf(100.00)).build();

        CarResponse carResponse = new CarResponse();
        carResponse.setId(1L);
        carResponse.setStatus(Status.AVAILABLE);
        carResponse.setMake("BMW");
        carResponse.setModel("M3");
        carResponse.setCategory(category);
        carResponse.setPrice(price);

        return carResponse;
    }

    private CarDetailResponse createSampleCarDetailResponse(){
        Price price = Price.builder()
                .forDay(BigDecimal.valueOf(400.00))
                .forTwoToFourDays(BigDecimal.valueOf(300.00))
                .forWeek(BigDecimal.valueOf(100.00)).build();

        Category category = Category.builder().name("coupe").build();

        CarDetailResponse carDetailResponse = new CarDetailResponse();
        carDetailResponse.setId(1L);
        carDetailResponse.setStatus(Status.AVAILABLE);
        carDetailResponse.setMake("BMW");
        carDetailResponse.setModel("M3");
        carDetailResponse.setProductionYear(2006);
        carDetailResponse.setPower(343);
        carDetailResponse.setFuelType(FuelType.PETROL);
        carDetailResponse.setTransmissionType(TransmissionType.MANUAL);
        carDetailResponse.setDriveType(DriveType.REAR);
        carDetailResponse.setPrice(price);
        carDetailResponse.setCategory(category);

        return carDetailResponse;
    }
}