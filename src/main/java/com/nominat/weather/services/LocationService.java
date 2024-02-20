package com.nominat.weather.services;

import com.nominat.weather.entity.Location;
import com.nominat.weather.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LocationService {

    //state Code only for USA, country code not required
    //public static final String LOCATION_URL = "http://api.openweathermap.org/geo/1.0/direct?q={cityName},{stateCode},{countryCode}&limit={limit}&appid={API key}";
    public static final String LOCATION_URL = "http://api.openweathermap.org/geo/1.0/direct?q={cityName}&limit={limit}&appid={API key}";

    @Autowired
    private LocationRepository locationRepository;

    @Value("${api.key}")
    private String apiKey;

    public Location getLocation(String cityName) {
        Location location = locationRepository.findByName(cityName).stream().findFirst().orElse(null);
        return location != null ? location : getLocationsFromAPIandSave(cityName, 1).get(0);
    }

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> searchByName(String name) {
        return locationRepository.findByName(name);
    }

    public List<Location> searchByLocalName(String localName) {
        return locationRepository.findByLocalNamesContains(localName);
    }

    public Location searchByLatAndLon(double lat, double lon) {
        return locationRepository.findByLatAndLon(lat, lon);
    }


    private List<Location> getLocationsFromAPIandSave(String cityName, int limit) {
        //limit is 1 for now to avoid problem with receiving multiple locations with the same name
        //and now receiving most popular location

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Location>> response = restTemplate.exchange(
                LOCATION_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Location>>() {},
                cityName, limit, apiKey
        );
        List<Location> locations = response.getBody();
        for (Location location : locations) {
            locationRepository.save(location);
        }
        return locations;
    }
}