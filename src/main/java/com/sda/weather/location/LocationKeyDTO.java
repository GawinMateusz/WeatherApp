package com.sda.weather.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationKeyDTO {
    @JsonProperty("Key")
    private String key;

}
