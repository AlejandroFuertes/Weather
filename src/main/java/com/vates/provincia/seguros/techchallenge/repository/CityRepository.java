package com.vates.provincia.seguros.techchallenge.repository;

import com.vates.provincia.seguros.techchallenge.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    boolean existsByNameContainingIgnoreCase(String name);
    boolean existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(String name, String country);
}
