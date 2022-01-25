package com.sda.weather.location;

import com.fasterxml.jackson.databind.DeserializationFeature;
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
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationRepositoryMock locationRepository = new LocationRepositoryMock();
        locationService = new LocationService(locationRepository);
    }

    @Test
    void createLocation_whenAllArgsAreProper_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", "Pomorskie");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("Pomorskie");
    }

    @Test
    void createLocation_whenNameIsEmpty_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("", 20.0, 30.0, "Polska", "Pomorskie"));
        // then
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenNameIsBlank_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("  ", 20.0, 30.0, "Polska", "Pomorskie"));
        // then
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIs90_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 90.0, 30.0, "Polska", "Pomorskie");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(90.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("Pomorskie");
    }

    @Test
    void createLocation_whenLatitudeIsMinus90_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", -90.0, 30.0, "Polska", "Pomorskie");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(-90.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("Pomorskie");
    }

    @Test
    void createLocation_whenLatitudeIs91_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Gdańsk", 91.0, 30.0, "Polska", "Pomorskie"));
        // then
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLatitudeIsMinus91_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Gdańsk", -91.0, 30.0, "Polska", "Pomorskie"));
        // then
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIs180_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 20.0, 180.0, "Polska", "Pomorskie");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(180.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("Pomorskie");
    }

    @Test
    void createLocation_whenLongitudeIsMinus180_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 20.0, -180.0, "Polska", "Pomorskie");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(-180.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isEqualTo("Pomorskie");
    }

    @Test
    void createLocation_whenLongitudeIs181_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Gdańsk", 20.0, 181.0, "Polska", "Pomorskie"));
        // then
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenLongitudeIsMinus181_throwsAnException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Gdańsk", 20.0, -181.0, "Polska", "Pomorskie"));
        // then
        assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createLocation_whenRegionIsEmpty_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", "");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isNull();
    }

    @Test
    void createLocation_whenRegionIsNull_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", null);
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isNull();
    }

    @Test
    void createLocation_whenRegionIsBlank_createsNewLocation() {
        // when
        Location location = locationService.createLocation("Gdańsk", 20.0, 30.0, "Polska", "  ");
        // then
        assertThat(location.getCityName()).isEqualTo("Gdańsk");
        assertThat(location.getLatitude()).isEqualTo(20.0);
        assertThat(location.getLongitude()).isEqualTo(30.0);
        assertThat(location.getCountryName()).isEqualTo("Polska");
        assertThat(location.getRegion()).isBlank();
    }


}
