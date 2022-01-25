package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.Instant;
import java.util.Optional;

@Data
public class ForecastRepository {

    private final SessionFactory sessionFactory;

    public Forecast save(Forecast forecast) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(forecast);
        transaction.commit();
        session.close();
        return forecast;
    }

    Optional<Forecast> findByLocationAndCreationDateAndForecastDate(Location location, Instant creationDate, Instant forecastDate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Forecast singleResult = session
                .createQuery("FROM Forecast WHERE id =?1 AND creationDate=?2 AND forecastDate=?3", Forecast.class)
                .setParameter(1, location.getId())
                .setParameter(2, creationDate)
                .setParameter(3, forecastDate).getSingleResult();
        transaction.commit();
        session.close();
        return Optional.of(singleResult);
    }
}
