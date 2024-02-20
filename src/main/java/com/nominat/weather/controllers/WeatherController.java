package com.nominat.weather.controllers;

import com.nominat.weather.entity.CurrentWeather;
import com.nominat.weather.repository.CurrentWeatherRepository;
import com.nominat.weather.repository.ForecastRepository;
import com.nominat.weather.repository.HistoricalWeatherRepository;
import com.nominat.weather.services.CurrentWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final CurrentWeatherService currentWeatherService;

    @Autowired
    public WeatherController(CurrentWeatherService currentWeatherService) {
        this.currentWeatherService = currentWeatherService;
    }


    @GetMapping("/weather")
    public CurrentWeather getCurrentWeather(@RequestParam(value = "city", defaultValue = "New York") String cityName) {
        return currentWeatherService.getCurrentWeatherByCity(cityName);
    }

}
