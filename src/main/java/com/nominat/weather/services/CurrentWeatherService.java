package com.nominat.weather.services;

import com.nominat.weather.entity.CurrentWeather;
import com.nominat.weather.entity.Forecast;
import com.nominat.weather.entity.HistoricalWeather;
import com.nominat.weather.repository.CurrentWeatherRepository;
import com.nominat.weather.repository.ForecastRepository;
import com.nominat.weather.repository.HistoricalWeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class CurrentWeatherService {
    private final CurrentWeatherRepository currentWeatherRepository;
    private final ForecastRepository forecastRepository;
    private final HistoricalWeatherRepository historicalWeatherRepository;

    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&";
    private final String apiKey = "";


    public CurrentWeatherService(CurrentWeatherRepository currentWeatherRepository, ForecastRepository forecastRepository, HistoricalWeatherRepository historicalWeatherRepository) {
        this.currentWeatherRepository = currentWeatherRepository;
        this.forecastRepository = forecastRepository;
        this.historicalWeatherRepository = historicalWeatherRepository;
    }

    public CurrentWeather getCurrentWeather(String cityName) {

        CurrentWeather currentWeather = currentWeatherRepository.findByNameAndDatetimeAfter(cityName, java.time.LocalDateTime.now().minusMinutes(30)).stream().findFirst().orElse(null);
        return Objects.requireNonNullElseGet(currentWeather, () -> getWeatherFromAPIandSave(cityName));

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

    private CurrentWeather getWeatherFromAPIandSave(String cityName) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrentWeather> response = restTemplate.getForEntity(OPEN_WEATHER_MAP_API, CurrentWeather.class, cityName, apiKey);
        CurrentWeather currentWeather = response.getBody();
        currentWeather.setDatetime(java.time.LocalDateTime.now());
        currentWeatherRepository.save(currentWeather);
        return currentWeather;
    }
}
