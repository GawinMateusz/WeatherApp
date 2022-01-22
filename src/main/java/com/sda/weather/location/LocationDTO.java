package com.sda.weather.location;

import lombok.Data;

@Data
public class LocationDTO {
    private Long id;
    private String cityName;
    private double latitude;
    private double longitude;
    private String countryName;
    private String region;
}
