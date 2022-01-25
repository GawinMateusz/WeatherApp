package com.sda.weather.forecast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ForecastController {

    private final ForecastService forecastService;
    private final ObjectMapper objectMapper;

    public String getForecast(Long locationId, Integer period){
        try {
        Forecast forecast = forecastService.getForecast(locationId, period);
        ForecastDTO forecastDTO = mapForecastToForecastDTO(forecast);
            return objectMapper.writeValueAsString(forecastDTO);
        } catch (JsonProcessingException e) {
            return String.format("{\"message\": \"%s\"}", e.getMessage());
        }
    }

    private ForecastDTO mapForecastToForecastDTO(Forecast forecast) {
        ForecastDTO forecastDTO = new ForecastDTO();
        forecastDTO.setTemperature(forecast.getTemperature());
        forecastDTO.setHumidity(forecast.getHumidity());
        forecastDTO.setWindDirection(forecast.getWindDirection());
        forecastDTO.setWindSpeed(forecast.getWindSpeed());
        return forecastDTO;
    }
}
