package com.project.carrental.mapper;

import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarResponse carToCarResponse(Car car);
}
