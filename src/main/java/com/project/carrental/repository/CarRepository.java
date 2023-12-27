package com.project.carrental.repository;

import com.project.carrental.model.Car;
import com.project.carrental.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByCategoryName(String categoryName);

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.category LEFT JOIN FETCH c.price")
    List<Car> findAllWithCategoryAndPrice();
}
