package com.nominat.weather.controllers;

import com.nominat.weather.entity.Location;
import com.nominat.weather.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    LocationService locationService;
    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public Location getLocation(@RequestParam(value = "city", defaultValue = "New York") String cityName) {
        return locationService.getLocation(cityName);
    }
}
