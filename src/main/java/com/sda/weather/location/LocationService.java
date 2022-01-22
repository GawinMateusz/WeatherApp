package com.sda.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final ObjectMapper objectMapper;

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

        try {
            String url = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=16IU0srbC2lqT28A8ZQF7afShZFON3pN&q=" + latitude + "%2C" + longitude + "&language=pl-pl";
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            LocationKeyDTO locationKeyDTO = objectMapper.readValue(responseBody, LocationKeyDTO.class);
            location.setLocationKey(locationKeyDTO.getKey());
        } catch (Exception ioException) {
            throw new IllegalArgumentException("Komunikacja z serwerem zewnętrznym nie powiodła się");
        }

        return locationRepository.save(location);
    }

    List<Location> getAllLocations() {
        return locationRepository.findAll();
    }


    public WeatherInfo getWeather(int locationNumber) {
        Location location = getAllLocations().get(locationNumber - 1);
        String locationKey = location.getLocationKey();
        WeatherInfo weatherInfo;
        try {
            String url = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + locationKey + "?apikey=16IU0srbC2lqT28A8ZQF7afShZFON3pN&language=pl-pl&metric=true";
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            weatherInfo = objectMapper.readValue(responseBody, WeatherInfo.class);
        } catch (Exception ioException) {
            throw new IllegalArgumentException("Komunikacja z serwerem zewnętrznym nie powiodła się");
        }
        return weatherInfo;
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
