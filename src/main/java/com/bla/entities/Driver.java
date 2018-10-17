package com.bla.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Driver {
    private Long id;
    private Car car;
    private String Name;
    private String phoneNumber;
    private String Stazh;
    private String bYear;
    private String stars;
}
