package com.sda.weather.location;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.forecast.ForecastController;
import com.sda.weather.forecast.ForecastRepository;
import com.sda.weather.forecast.ForecastService;
import com.sda.weather.frontend.UserInterface;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationRepositoryImpl locationRepositoryImpl = new LocationRepositoryImpl(sessionFactory);
        LocationService locationService = new LocationService(locationRepositoryImpl);
        LocationController locationController = new LocationController(objectMapper,locationService);
        ForecastRepository forecastRepository = new ForecastRepository(sessionFactory);
        ForecastService forecastService = new ForecastService(objectMapper,locationService,forecastRepository);
        ForecastController forecastController = new ForecastController(forecastService, objectMapper);
        UserInterface userInterface = new UserInterface(locationController,forecastController);
        userInterface.run();
    }
}
