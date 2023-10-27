package com.project.carrental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
@Check(constraints = "start_date <= end_date")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Car car;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    private BigDecimal cost;
}
