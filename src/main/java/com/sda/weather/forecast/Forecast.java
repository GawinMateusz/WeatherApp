package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float temperature;
    private int pressure;
    private int humidity;
    private int windDirection;
    private float windSpeed;
    @ManyToOne
    Location location;
    Instant creationDate;
    Instant forecastDate;

}
