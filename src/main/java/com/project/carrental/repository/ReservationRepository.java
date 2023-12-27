package com.project.carrental.repository;

import com.project.carrental.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.driver LEFT JOIN FETCH r.car LEFT JOIN FETCH r.car.category LEFT JOIN FETCH r.car.price")
    List<Reservation> findAllWithDriverAndCar();
}
