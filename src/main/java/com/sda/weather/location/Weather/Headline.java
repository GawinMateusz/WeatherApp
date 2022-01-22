package com.sda.weather.location.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Headline {
    @JsonProperty("EffectiveDate")
    public Date effectiveDate;
    @JsonProperty("EffectiveEpochDate")
    public int effectiveEpochDate;
    @JsonProperty("Severity")
    public int severity;
    @JsonProperty("Text")
    public String text;
    @JsonProperty("Category")
    public String category;
    @JsonProperty("EndDate")
    public Date endDate;
    @JsonProperty("EndEpochDate")
    public int endEpochDate;
    @JsonProperty("MobileLink")
    public String mobileLink;
    @JsonProperty("Link")
    public String link;
}
