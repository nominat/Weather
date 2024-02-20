package com.nominat.weather.services;

import com.nominat.weather.entity.Location;
import com.nominat.weather.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

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
}