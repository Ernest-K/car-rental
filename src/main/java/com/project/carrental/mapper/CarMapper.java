package com.project.carrental.mapper;

import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.dto.response.CarResponse;
import com.project.carrental.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "id")
    CarResponse carToCarResponse(Car car);
    CarDetailResponse carToCarDetailResponse(Car car);
}
