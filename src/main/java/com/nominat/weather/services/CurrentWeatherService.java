package com.nominat.weather.services;

import com.nominat.weather.entity.CurrentWeather;
import com.nominat.weather.entity.Forecast;
import com.nominat.weather.entity.HistoricalWeather;
import com.nominat.weather.repository.CurrentWeatherRepository;
import com.nominat.weather.repository.ForecastRepository;
import com.nominat.weather.repository.HistoricalWeatherRepository;

public class CurrentWeatherService {
    private final CurrentWeatherRepository currentWeatherRepository;
    private final ForecastRepository forecastRepository;
    private final HistoricalWeatherRepository historicalWeatherRepository;

    public CurrentWeatherService(CurrentWeatherRepository currentWeatherRepository, ForecastRepository forecastRepository, HistoricalWeatherRepository historicalWeatherRepository) {
        this.currentWeatherRepository = currentWeatherRepository;
        this.forecastRepository = forecastRepository;
        this.historicalWeatherRepository = historicalWeatherRepository;
    }

    public CurrentWeather getCurrentWeather(String location) {
        return currentWeatherRepository.findByLocation(location).stream().findFirst().orElse(null);
    }

//    public Forecast getForecast(String location) {
//        return forecastRepository.findByLocation(location).stream().findFirst().orElse(null);
//    }
//
//    public HistoricalWeather getHistoricalWeather(String location) {
//        return historicalWeatherRepository.findByLocation(location).stream().findFirst().orElse(null);
//    }

    public void saveCurrentWeather(CurrentWeather currentWeather) {
        currentWeatherRepository.save(currentWeather);
    }

    public void saveForecast(Forecast forecast) {
        forecastRepository.save(forecast);
    }

    public void saveHistoricalWeather(HistoricalWeather historicalWeather) {
        historicalWeatherRepository.save(historicalWeather);
    }

    public void deleteCurrentWeather(CurrentWeather currentWeather) {
        currentWeatherRepository.delete(currentWeather);
    }

    public void deleteForecast(Forecast forecast) {
        forecastRepository.delete(forecast);
    }

    public void deleteHistoricalWeather(HistoricalWeather historicalWeather) {
        historicalWeatherRepository.delete(historicalWeather);
    }
}
