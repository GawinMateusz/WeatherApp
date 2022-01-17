package com.sda.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class LocationServiceTest {


    LocationService locationService;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        LocationRepositoryMock locationRepository = new LocationRepositoryMock();
        locationService = new LocationService(locationRepository);
    }

    @Test
    void createLocation_whenAllArgsAreProper_createsNewLocation() {
        Location location = locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", "Pomorskie");
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("Pomorskie");
    }

    @Test
    void createLocation_whenNameIsEmpty_throwsAnException() {

        Throwable throwable = catchThrowable(() -> locationService.createLocation("", 20.0, 30.0, "Polska", "Pomorskie"));

        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsWrong_throwsAnException() {

        Throwable throwable = catchThrowable(() -> locationService.createLocation("Gdańsk", 120.0, 30.0, "Polska", "Pomorskie"));

        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenRegionIsEmpty_createsNewLocation() {

        Location location = locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", "");

        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("");
    }

    @Test
    void createLocation_whenRegionIsEmpty_throwableIsNull() {

        Throwable throwable = catchThrowable(() -> locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", ""));

        assertThat(throwable).isNull();
    }
}
