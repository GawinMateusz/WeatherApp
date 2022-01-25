package com.sda.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    Location createLocation(String cityName, double latitude, double longitude, String countryName, String region) {
        validation(cityName, latitude, longitude, countryName);
        Location location = new Location();
        location.setCityName(cityName);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setCountryName(countryName);
        if (region != null && !region.isBlank()) {
            location.setRegion(region);
        }
        return locationRepository.save(location);
    }

    List<Location> getAllLocations() {
        return locationRepository.findAll();
    }


    public Optional<Location> getLocation(Long id) {
        return locationRepository.findById(id);
    }

    private void validation(String cityName, double latitude, double longitude, String countryName) {
        if (cityName == null) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Nie wpisano nazwy miasta.");
        } else if (cityName.isBlank()) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Nazwa miasta zawiera puste znaki");
        } else if (latitude < -90.0d) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Wpisano szerokość geograficzna większą niż 90 stopni S");
        } else if (latitude > 90.0d) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Wpisano szerokość geograficzna większą niż 90 stopni N");
        } else if (longitude < -180.0d) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Wpisano dlugość geograficzna większą niż 180 stopni W");
        } else if (longitude > 180.0d) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Wpisano dlugość geograficzna większą niż 180 stopni E");
        } else if (countryName == null) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Nie wpisano nazwy państwa.");
        } else if (countryName.isBlank()) {
            throw new IllegalArgumentException("Walidacja nie powiodła się! Nazwa państwa zawiera puste znaki");
        }
    }
}
