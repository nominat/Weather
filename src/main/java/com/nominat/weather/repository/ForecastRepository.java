package com.nominat.weather.repository;

import com.nominat.weather.entity.Forecast;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForecastRepository extends MongoRepository<Forecast, String> {
}