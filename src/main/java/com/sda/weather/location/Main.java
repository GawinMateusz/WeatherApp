package com.sda.weather.location;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        LocationService locationService = new LocationService(locationRepositoryImpl, objectMapper);
        LocationController locationController = new LocationController(objectMapper, locationService);

        UserInterface userInterface = new UserInterface(locationController);
        userInterface.run();
    }
}
