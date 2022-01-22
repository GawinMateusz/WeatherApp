package com.sda.weather.location.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {
    @JsonProperty("Minimum")
    public Minimum minimum;
    @JsonProperty("Maximum")
    public Maximum maximum;
}