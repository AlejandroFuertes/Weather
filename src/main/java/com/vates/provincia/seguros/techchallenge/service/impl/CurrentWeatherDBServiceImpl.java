package com.vates.provincia.seguros.techchallenge.service.impl;

import com.vates.provincia.seguros.techchallenge.converter.CityConverter;
import com.vates.provincia.seguros.techchallenge.converter.WeatherConverter;
import com.vates.provincia.seguros.techchallenge.dto.Location;
import com.vates.provincia.seguros.techchallenge.dto.WeatherCurrentConditions;
import com.vates.provincia.seguros.techchallenge.dto.response.WeatherCityResponse;
import com.vates.provincia.seguros.techchallenge.entity.CityEntity;
import com.vates.provincia.seguros.techchallenge.entity.WeatherEntity;
import com.vates.provincia.seguros.techchallenge.exception.CityNotFoundException;
import com.vates.provincia.seguros.techchallenge.exception.CountryNotFoundException;
import com.vates.provincia.seguros.techchallenge.exception.WeatherNotFoundException;
import com.vates.provincia.seguros.techchallenge.repository.CityRepository;
import com.vates.provincia.seguros.techchallenge.repository.WeatherRepository;
import com.vates.provincia.seguros.techchallenge.service.CurrentWeatherDBService;
import com.vates.provincia.seguros.techchallenge.service.LoadDBService;
import com.vates.provincia.seguros.techchallenge.service.WeatherClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CurrentWeatherDBServiceImpl implements CurrentWeatherDBService {

    private static final int MINUTE_EXPIRATION = 40;

    private final WeatherClientService weatherClientService;
    private final LoadDBService loadDBService;
    private final CityConverter cityConverter;
    private final WeatherConverter weatherConverter;
    private final CityRepository cityRepository;
    private final WeatherRepository  weatherRepository;

    @Autowired
    public CurrentWeatherDBServiceImpl(WeatherClientService weatherClientService,
                                       LoadDBService loadDBService,
                                       CityConverter cityConverter,
                                       WeatherConverter weatherConverter,
                                       CityRepository cityRepository,
                                       WeatherRepository weatherRepository) {

        this.weatherClientService = weatherClientService;
        this.loadDBService = loadDBService;
        this.cityConverter = cityConverter;
        this.weatherConverter = weatherConverter;
        this.cityRepository = cityRepository;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public List<WeatherCityResponse> currentForecastByCity(String city) {

        log.info("Buscando pronostico actual por ciudad: '{}'", city);
        if(cityRepository.existsByNameContainingIgnoreCase(city)) {
            return handleExistCity(city);
        } else {
            return handleNotExistCity(city);
        }
    }

    @Override
    public WeatherCityResponse currentForecastByCityAndCountry(String city, String country) {
        log.info("Buscando pronostico actual por ciudad '{}' y pais '{}'", city, country);
        if(cityRepository.existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(city, country)) {
            return handleExistCityAndCountry(city, country);
        } else {
            return handleNotExistCityAndCountry(city, country);
        }
    }

    private WeatherCityResponse handleNotExistCityAndCountry(String city, String country) {
        log.info("La ciudad '{}' del pais '{}' no se encuentra en la base de datos, se procede a buscar en la API",
                city,
                country);

        List<CityEntity> cityEntities = searchCityInAPI(city);
        CityEntity  cityEntity = getCityByCountry(cityEntities, country);

        WeatherEntity weatherEntity = getCurrentForecastByCityFromAPI(cityEntity);
        weatherRepository.save(weatherEntity);

        log.info("Ciudad '{}' y su pronostico fueron guardados en la base de datos.", city);
        return weatherConverter.convertEntityToResponse(weatherEntity);
    }

    private CityEntity getCityByCountry(List<CityEntity> cityEntities, String country) {
        Optional<CityEntity> cityEntity = cityEntities.stream()
                .filter(c -> c.getCountry().equalsIgnoreCase(country))
                .findFirst();

        if(cityEntity.isPresent()) {
            loadDBService.saveCity(cityEntity.get());
            return cityEntity.get();
        }

        //y tambien devuelvo una excepcion personalizada
        //MENSAJE DE ERROR CUANDO NO ENCUENTRA UNA CIUDAD MEDIANTE UN PAIS
        log.error("No se encontro la ciudad asociada al pais '{}'.", country);
        throw new CountryNotFoundException("No se encontro la ciudad asociada al pais '" + country + "'.", HttpStatus.NOT_FOUND);
    }

    private WeatherCityResponse handleExistCityAndCountry(String city, String country) {
        log.info("Se encontraron resultados en la base de datos.");
        WeatherEntity weatherEntity = weatherRepository.findByCityNameContainingIgnoreCaseAndCity_CountryContainingIgnoreCase(city, country);

        if(isWeatherOutdated(weatherEntity)) {
            log.info("El ultimo pronostico guardado para la ciudad '{}' del pais '{}' ha superado los '{}' minutos",
                    weatherEntity.getCity().getName(),
                    weatherEntity.getCity().getCountry(),
                    MINUTE_EXPIRATION);
            updateWeatherFromApi(weatherEntity);
        }
        return weatherConverter.convertEntityToResponse(weatherEntity);
    }

    private List<WeatherCityResponse> handleExistCity(String city) {
        log.info("Se encontraron resultados en la base de datos.");
        List<WeatherEntity> weatherEntities = currentForecastDB(city);
        return weatherConverter.convertEntitiesToListResponse(weatherEntities);
    }

    private List<WeatherCityResponse> handleNotExistCity(String city) {
        log.info("La ciudad '{}' no se encuentra en la base de datos, se procede a buscar en la API", city);
        List<CityEntity> cities = searchCityInAPI(city);

        if(cities.isEmpty()) {
            throw new CityNotFoundException("No se encontro la ciudad '" + city + "'.", HttpStatus.NOT_FOUND);
        }
        loadDBService.saveCities(cities);
        List<WeatherEntity> weatherEntities = cities.stream()
                .map(this::getCurrentForecastByCityFromAPI)
                .collect(Collectors.toList());

        if(weatherEntities.isEmpty()) {
            throw new WeatherNotFoundException("No se encontro el pronostico de la ciudad '" + city + "'.", HttpStatus.NOT_FOUND);
        }

        weatherRepository.saveAll(weatherEntities);
        log.info("Ciudad '{}' y su pronostico fueron guardados en la base de datos.", city);
        return weatherConverter.convertEntitiesToListResponse(weatherEntities);
    }

    private List<WeatherEntity> currentForecastDB(String city) {
        log.info("Buscando pronostico actual en la base de datos.");
        List<WeatherEntity> weatherEntities = weatherRepository.findByCityNameContainingIgnoreCase(city);

        for (WeatherEntity weather: weatherEntities) {
            if(isWeatherOutdated(weather)) {
                log.info("El ultimo pronostico guardado para la ciudad '{}' con codigo de ubicacion '{}' ha superado los '{}' minutos",
                        weather.getCity().getName(),
                        weather.getCity().getCodeLocation(),
                        MINUTE_EXPIRATION);
                updateWeatherFromApi(weather);
            }
        }
        return weatherEntities;
    }

    private boolean isWeatherOutdated(WeatherEntity weather) {
        long timeDifferenceInMillis = new Date().getTime() - weather.getLastUpdated().getTime();
        return timeDifferenceInMillis > (MINUTE_EXPIRATION * 60 * 1000);
    }

    private void updateWeatherFromApi(WeatherEntity weather) {

        log.info("Actualizando clima de la ciudad '{}' con codigo de ubicacion '{}' a traves de la API",
                weather.getCity().getName(),
                weather.getCity().getCodeLocation());

        List<WeatherCurrentConditions> weatherCurrentConditions = weatherClientService
                .getCurrentConditions(weather.getCity().getCodeLocation());

        for (WeatherCurrentConditions weatherCurrent: weatherCurrentConditions) {

            weather.setCondition(weatherCurrent.getWeatherCondition());
            weather.setTemperatureValue(String.valueOf(weatherCurrent.getTemperature().getMetric().getValue()));
            weather.setTemperatureUnit(weatherCurrent.getTemperature().getMetric().getUnit());
            weather.setWindSpeed(String.valueOf(weatherCurrent.getWind().getSpeedResponse().getMetric().getValue()));
            weather.setWindUnit(weatherCurrent.getWind().getSpeedResponse().getMetric().getUnit());
            weather.setRelativeHumidity(weatherCurrent.getRelativeHumidity());
            weather.setLastUpdated(getCurrentDate());
            weatherRepository.save(weather);

            log.info("Clima Actualizado");
        }
    }

    private List<CityEntity> searchCityInAPI(String city) {

        try {
            log.info("Buscando ciudades en la API con la ciudad '{}'.", city);
            List<Location> locations = weatherClientService.getCitiesByText(city);
            List<CityEntity> cities = new ArrayList<>();
            for (Location location: locations) {
                cities.add(cityConverter.convertLocationToCityEntity(location));
            }
            return cities;
        } catch (Exception e) {
            log.error("Error durante la busqueda en la API: " + e.getMessage());
            throw new CityNotFoundException("Error al buscar las ciudades en la API. " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private WeatherEntity getCurrentForecastByCityFromAPI(CityEntity cityEntity) {
        try {
            log.info("Buscando pronostico actual para la ciudad '{}' en la API.",
                    cityEntity.getName());

            List<WeatherCurrentConditions> weatherCurrentConditions = weatherClientService
                    .getCurrentConditions(cityEntity.getCodeLocation());

            log.info("Pronostico actual obtenido.");

            WeatherEntity entity = new WeatherEntity();

            for (WeatherCurrentConditions weather: weatherCurrentConditions) {
                entity = weatherConverter.convertDtoToEntity(weather);
                entity.setCity(cityEntity);
                entity.setLastUpdated(new Date());
            }
            return entity;

        } catch (Exception e) {
            log.error("Error durante la busqueda en la API: " + e.getMessage());
            throw new WeatherNotFoundException("Error al obtener el clima para la ciudad. " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private Date getCurrentDate() {
        return new Date();
    }
}
