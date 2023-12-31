package com.project.carrental.dto.response;

import com.project.carrental.model.Category;
import com.project.carrental.model.Price;
import com.project.carrental.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponse {
    private Long id;
    private Status status;
    private String make;
    private String model;
    private Price price;
    private Category category;
}
