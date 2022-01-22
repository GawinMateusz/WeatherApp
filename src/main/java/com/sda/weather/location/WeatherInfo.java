package com.sda.weather.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sda.weather.location.Weather.DailyForecast;
import com.sda.weather.location.Weather.Headline;
import lombok.Data;

import java.util.ArrayList;

@Data
public class WeatherInfo {

    @JsonProperty("Headline")
    public Headline headline;
    @JsonProperty("DailyForecasts")
    public ArrayList<DailyForecast> dailyForecasts;

}
