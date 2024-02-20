package com.nominat.weather.repository;

import com.nominat.weather.entity.CurrentWeather;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CurrentWeatherRepository extends MongoRepository<CurrentWeather, String> {
    List<CurrentWeather> findByName(String cityName);

    List<CurrentWeather> findByNameAndDatetimeAfter(String cityName, java.time.LocalDateTime datetime);
    List<CurrentWeather> findByCoordAndDatetimeAfter(CurrentWeather.Coord coord, java.time.LocalDateTime datetime);
    List<CurrentWeather> findAll();
    CurrentWeather save(CurrentWeather currentWeather);

    void delete(CurrentWeather currentWeather);

}