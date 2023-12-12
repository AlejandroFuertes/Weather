package com.vates.provincia.seguros.techchallenge.converter;

import com.vates.provincia.seguros.techchallenge.dto.Location;
import com.vates.provincia.seguros.techchallenge.entity.CityEntity;
import org.springframework.stereotype.Component;

@Component
public class CityConverter {

    public CityEntity convertLocationToCityEntity(Location location) {
        return CityEntity.builder()
                .name(location.getLocalizedName())
                .codeLocation(location.getLocationKey())
                .country(location.getCountry().getName())
                .codeCountry(location.getCountry().getId())
                .build();

    }
}
