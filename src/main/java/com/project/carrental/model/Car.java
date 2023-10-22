package com.project.carrental.model;

import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String make;

    private String model;

    private int year;

    private int power;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    @OneToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private Price price;
}
