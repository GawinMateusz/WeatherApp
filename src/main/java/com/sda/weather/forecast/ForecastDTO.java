package com.sda.weather.forecast;

import lombok.Data;

@Data
public class ForecastDTO {

    private float temperature;
    private int pressure;
    private int humidity;
    private int windDirection;
    private float windSpeed;
}
