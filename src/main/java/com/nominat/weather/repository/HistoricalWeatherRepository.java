package com.nominat.weather.repository;

import com.nominat.weather.entity.HistoricalWeather;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricalWeatherRepository extends MongoRepository<HistoricalWeather, String> {
}