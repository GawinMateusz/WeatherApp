package com.sda.weather.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.Location;
import com.sda.weather.location.LocationService;
import lombok.Data;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class ForecastService {

    private final ObjectMapper objectMapper;
    private final LocationService locationService;
    private final ForecastRepository forecastRepository;
    private final String apiKey = "77227cc614b7db0ffaa5b12e94b67696";
    Location location;

    public Forecast getForecast(Long locationId, Integer period) {
        Forecast forecast;
        if(locationService.getLocation(locationId).isPresent()) {
            location = locationService.getLocation(locationId).get();
        } else {
            throw new IllegalArgumentException("Brak podanej lokalizacji.");
        } if (period == null){
            period = 2;
        } else if (period > 7){
            throw new IllegalArgumentException("Wpisano błędny dzień dla uzyskania informacji pogodowych");
        }
        try {
            String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + apiKey;
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            ForecastClientResponse forecastClientResponse = objectMapper.readValue(responseBody, ForecastClientResponse.class);
            forecast = mapForecastClientResponseToForecast(forecastClientResponse, period);
            forecast.setCreationDate(Instant.now().truncatedTo(ChronoUnit.DAYS));
            forecast.setForecastDate(Instant.now().truncatedTo(ChronoUnit.DAYS).plus(period,ChronoUnit.DAYS));
        } catch (Exception ioException) {
            throw new IllegalArgumentException("Komunikacja z serwerem zewnętrznym nie powiodła się");
        }
        return forecastRepository.save(forecast);
    }

    public Forecast mapForecastClientResponseToForecast(ForecastClientResponse forecastClientResponse, Integer period){
        Forecast forecast = null;

        Stream<ForecastClientResponse.SingleForecast> singleForecastStream = forecastClientResponse.getDaily().stream().filter(a -> a.getTimestamp().equals(Instant.now().plus(period, ChronoUnit.DAYS).getEpochSecond()));
//        Integer humidity = singleForecastStream.map(a -> a.getHumidity()).collect(Collectors.toList()).get(0);
//        forecast.setHumidity(humidity);
        Integer pressure = singleForecastStream.map(a -> a.getPressure()).collect(Collectors.toList()).get(0);
        forecast.setPressure(pressure);
        Float temp = singleForecastStream.map(a -> a.getTemperature().getDay()).collect(Collectors.toList()).get(0);
        forecast.setTemperature(temp);
        Float windSpeed = singleForecastStream.map(a -> a.getWindSpeed()).collect(Collectors.toList()).get(0);
        forecast.setWindSpeed(windSpeed);
        Integer windDirection = singleForecastStream.map(a -> a.getWindDeg()).collect(Collectors.toList()).get(0);
        forecast.setWindDirection(windDirection);
        return forecast;
    }
}
