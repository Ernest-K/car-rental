package com.project.carrental.model;

import com.project.carrental.model.enums.DriveType;
import com.project.carrental.model.enums.FuelType;
import com.project.carrental.model.enums.Status;
import com.project.carrental.model.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
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

    private Integer productionYear;

    private Integer power;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    @OneToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private Price price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "car")
    private List<Reservation> reservations;
}
