package com.sda.weather.location;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.Weather.DailyForecast;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LocationController {

    private final ObjectMapper objectMapper;
    private final LocationService locationService;

    public String createLocation(String inputData) {
        try {
            LocationDTO locationDTOFromMapper = objectMapper.readValue(inputData, LocationDTO.class);
            Location location = locationService.createLocation(
                    locationDTOFromMapper.getCityName(),
                    locationDTOFromMapper.getLatitude(),
                    locationDTOFromMapper.getLongitude(),
                    locationDTOFromMapper.getCountryName(),
                    locationDTOFromMapper.getRegion());
            LocationDTO locationDTO = mapLocationToLocationDTO(location);
            return objectMapper.writeValueAsString(locationDTO);
        } catch (Exception e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }

    public String getAllLocationsToString() {
        try {
            List<Location> allLocations = locationService.getAllLocations();
            List<LocationDTO> allLocationsDTO = allLocations.stream()
                    .map(this::mapLocationToLocationDTO)
                    .collect(Collectors.toList());
            return objectMapper.writeValueAsString(allLocationsDTO);
        } catch (JsonProcessingException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }

    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    public String getWeather(int locationNumber, LocalDate parsedDate) {
        String respond;
        ArrayList<DailyForecast> dailyForecasts = locationService.getWeather(locationNumber).dailyForecasts;
        for (DailyForecast dailyForecast : dailyForecasts) {
            LocalDate localDate = dailyForecast.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (localDate.compareTo(parsedDate) == 0) {
                respond = String.format("{\"date\":\"%s\"," +
                                "\"MAXTemp\":\"%s\"," +
                                "\"MINTemp\":\"%s\"," +
                                "\"zachmurzenie/opady\":\"%s\"}",
                        dailyForecast.date,
                        dailyForecast.temperature.maximum.value,
                        dailyForecast.temperature.minimum.value,
                        dailyForecast.day.iconPhrase
                );
                return respond;
            }
        }
        return null;
    }

    private LocationDTO mapLocationToLocationDTO(Location newLocation) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(newLocation.getId());
        locationDTO.setCityName(newLocation.getCityName());
        locationDTO.setLatitude(newLocation.getLatitude());
        locationDTO.setLongitude(newLocation.getLongitude());
        locationDTO.setCountryName(newLocation.getCountryName());
        locationDTO.setRegion(newLocation.getRegion());
        return locationDTO;
    }
}
