package com.project.carrental.dto.response;

import com.project.carrental.model.Category;
import com.project.carrental.model.Price;
import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponse {
    private Status status;
    private String make;
    private String model;
    private Integer productionYear;
    private Integer power;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private DriveType driveType;
    private Price price;
    private Category category;
}
