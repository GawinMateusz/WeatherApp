package com.sda.weather.location;

import java.util.Collections;
import java.util.List;

public class LocationRepositoryMock implements LocationRepository {

    @Override
    public Location save(Location location) {
        location.setId(1L);
        return location;
    }

    @Override
    public List<Location> findAll() {
        return Collections.emptyList();
    }

}
