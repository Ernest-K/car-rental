package com.project.carrental.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double perDay;
    private double twoToFourDays;
    private double perWeek;
}
