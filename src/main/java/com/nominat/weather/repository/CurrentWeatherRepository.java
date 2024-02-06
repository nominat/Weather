package com.nominat.weather.repository;

import com.nominat.weather.entity.CurrentWeather;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CurrentWeatherRepository extends MongoRepository<CurrentWeather, String> {
    List<CurrentWeather> findByLocation(String location);
    // To save or update a document, you can use the save method
    CurrentWeather save(CurrentWeather currentWeather);

    // To delete a document, you can use the delete method
    void delete(CurrentWeather currentWeather);

}