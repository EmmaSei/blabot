package com.bla.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    private String number;
    private String marka;
    private String model;
    private String color;

    public Car() {
    }
}
