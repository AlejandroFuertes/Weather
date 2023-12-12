package com.vates.provincia.seguros.techchallenge.repository;

import com.vates.provincia.seguros.techchallenge.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    List<WeatherEntity> findByCityNameContainingIgnoreCase(String cityName);
    WeatherEntity findByCityNameContainingIgnoreCaseAndCity_CountryContainingIgnoreCase(String cityName, String country);
}
