package com.vates.provincia.seguros.techchallenge.service;

import com.vates.provincia.seguros.techchallenge.entity.CityEntity;

import java.util.List;

public interface LoadDBService {

    void saveCity(CityEntity city);
    void saveCities(List<CityEntity> cities);
}
