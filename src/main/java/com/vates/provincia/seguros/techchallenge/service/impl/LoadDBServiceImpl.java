package com.vates.provincia.seguros.techchallenge.service.impl;

import com.vates.provincia.seguros.techchallenge.entity.CityEntity;
import com.vates.provincia.seguros.techchallenge.repository.CityRepository;
import com.vates.provincia.seguros.techchallenge.service.LoadDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadDBServiceImpl implements LoadDBService {

    private final CityRepository cityRepository;

    @Autowired
    public LoadDBServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void saveCity(CityEntity city) {
        cityRepository.save(city);
    }

    @Override
    public void saveCities(List<CityEntity> cities) {
        cityRepository.saveAll(cities);
    }
}
