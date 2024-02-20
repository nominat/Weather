package com.nominat.weather.services;

import com.nominat.weather.entity.CurrentWeather;
import com.nominat.weather.entity.Forecast;
import com.nominat.weather.entity.HistoricalWeather;
import com.nominat.weather.entity.Location;
import com.nominat.weather.repository.CurrentWeatherRepository;
import com.nominat.weather.repository.ForecastRepository;
import com.nominat.weather.repository.HistoricalWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class CurrentWeatherService {
    private final CurrentWeatherRepository currentWeatherRepository;
    private final ForecastRepository forecastRepository;
    private final HistoricalWeatherRepository historicalWeatherRepository;
    private final LocationService locationService;

    private static final String OPEN_WEATHER_MAP_API_BY_CITY = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric&";
    private static final String OPEN_WEATHER_MAP_API_BY_COORDINATES = "http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}&units=metric&";

    @Value("${api.key}")
    private String apiKey;


    public CurrentWeatherService(CurrentWeatherRepository currentWeatherRepository,
                                 ForecastRepository forecastRepository,
                                 HistoricalWeatherRepository historicalWeatherRepository,
                                 LocationService locationService) {
        this.currentWeatherRepository = currentWeatherRepository;
        this.forecastRepository = forecastRepository;
        this.historicalWeatherRepository = historicalWeatherRepository;
        this.locationService = locationService;

    }

    public CurrentWeather getCurrentWeatherByCity(String cityName) {
        Location location = locationService.getLocation(cityName);
        return getCurrentWeatherByCoordinates(location.getLat(), location.getLon());
    }
    public CurrentWeather getCurrentWeatherByCoordinates(double lat, double lon) {
        CurrentWeather.Coord coord = new CurrentWeather.Coord();
        coord.setLat(lat);
        coord.setLon(lon);
        CurrentWeather currentWeather = currentWeatherRepository.findByCoordAndDatetimeAfter(coord, java.time.LocalDateTime.now().minusMinutes(30)).stream().findFirst().orElse(null);
        return Objects.requireNonNullElseGet(currentWeather, () -> getWeatherFromAPIandSave(lat, lon));
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
        ResponseEntity<CurrentWeather> response = restTemplate.getForEntity(OPEN_WEATHER_MAP_API_BY_CITY, CurrentWeather.class, cityName, apiKey);
        CurrentWeather currentWeather = response.getBody();
        currentWeather.setDatetime(java.time.LocalDateTime.now());
        currentWeatherRepository.save(currentWeather);
        return currentWeather;
    }
    private CurrentWeather getWeatherFromAPIandSave(double lat, double lon) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrentWeather> response = restTemplate.getForEntity(OPEN_WEATHER_MAP_API_BY_COORDINATES, CurrentWeather.class, lat, lon, apiKey);
        CurrentWeather currentWeather = response.getBody();
        currentWeather.setDatetime(java.time.LocalDateTime.now());
        currentWeatherRepository.save(currentWeather);
        return currentWeather;
    }
}
