package com.sda.weather.forecast;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ForecastClientResponse {

    private List<SingleForecast> daily;

    @Data
    static class SingleForecast {

        @JsonProperty("dt")
        private Long timestamp;
        @JsonProperty("temp")
        private Temperature temperature;
        private int pressure;
        private int humidity;
        @JsonProperty("wind_speed")
        private float windSpeed;
        @JsonProperty("wind_deg")
        private int windDeg;

        @Data
        static class Temperature {

            private float day;
        }
    }
}
