package com.bla.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Trip {
    private Driver driver;
    private Peshehod peshehod;
    private Date dateTime;
    private String fromPlace;
    private String toPlace;
}
