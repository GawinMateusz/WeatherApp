package com.sda.weather.location;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    Location createLocation(String cityName, double latitude, double longitude, String countryName, String region) {
        validateLocation(cityName, latitude, longitude, countryName); // todo I think you can inline this code instead of creating a method
        Location location = new Location();
        location.setCityName(cityName);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setCountryName(countryName);
        location.setRegion(region); // todo if user pass "    " value we don't want to store it
        return locationRepository.save(location);
    }

    void validateLocation(String cityName, double latitude, double longitude, String countryName) {
        if (cityName == null || cityName.isBlank() || latitude < -90.0d || latitude > 90.0d || longitude < -180.0d || longitude > 180.0d || countryName == null || countryName.isBlank()) {
            throw new IllegalArgumentException("Niepoprawne dane!"); // todo specify what went wrong
        }
    }
}
