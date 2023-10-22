package com.project.carrental.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequest {

    @Size(max = 30, message = "Status not valid")
    @NotBlank(message = "Status is required")
    private String status;

    @Size(max = 30, message = "Make is too long (max 30 characters)")
    @NotBlank(message = "Make is required")
    private String make;

    @Size(max = 30, message = "Model is too long (max 30 characters)")
    @NotBlank(message = "Model is required")
    private String model;

    @Min(0)
    @NotNull(message = "Production year is required")
    private Integer productionYear;

    @Min(0)
    @NotNull(message = "Production year is required")
    private Integer power;

    @Size(max = 30, message = "Fuel type is too long (max 30 characters)")
    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    @Size(max = 30, message = "Transmission type is too long (max 30 characters)")
    @NotBlank(message = "Transmission type is required")
    private String transmissionType;

    @Size(max = 30, message = "Drive type is too long (max 30 characters)")
    @NotBlank(message = "Drive type is required")
    private String driveType;

    @Min(0)
    @NotNull(message = "Price per dey is required")
    private double pricePerDay;

    @Min(0)
    @NotNull(message = "Price for two to four days is required")
    private double priceForTwoToFourDays;

    @Min(0)
    @NotNull(message = "Price per week is required")
    private double pricePerWeek;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
