package com.sda.weather.location.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Night {
    @JsonProperty("Icon")
    public int icon;
    @JsonProperty("IconPhrase")
    public String iconPhrase;
    @JsonProperty("HasPrecipitation")
    public boolean hasPrecipitation;
    @JsonProperty("PrecipitationType")
    public String precipitationType;
    @JsonProperty("PrecipitationIntensity")
    public String precipitationIntensity;
}
