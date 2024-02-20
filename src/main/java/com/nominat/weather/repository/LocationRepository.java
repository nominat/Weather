package com.nominat.weather.repository;

import com.nominat.weather.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByName(String name);
    List<Location> findByLocalNamesContains(String localName);
    Location findByLatAndLon(double lat, double lon);
}