package com.vates.provincia.seguros.techchallenge.controller;

import com.vates.provincia.seguros.techchallenge.dto.response.WeatherCityResponse;
import com.vates.provincia.seguros.techchallenge.service.CurrentWeatherDBService;
import com.vates.provincia.seguros.techchallenge.service.LoadDBService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = "Weather")
public class WeatherDBController {

    private final CurrentWeatherDBService currentWeatherDBService;

    @Autowired
    public WeatherDBController(CurrentWeatherDBService  currentWeatherDBService) {
        this.currentWeatherDBService = currentWeatherDBService;
    }

    @ApiOperation(value = "Obtener el pronostico actual por ciudad",
            notes = "Obtener el pronostico actual por ciudad.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operacion Exitosa", response = WeatherCityResponse.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Ciudad no encontrada"),
            @ApiResponse(code = 500, message = "Error interno del servidor"),
    })
    @GetMapping("/pronostico/city")
    public ResponseEntity<List<WeatherCityResponse>> currentForecastByCity(
            @ApiParam(value = "Nombre de la ciudad para la consulta", required = true)
            @RequestParam(name = "city") String city) {
        List<WeatherCityResponse> weathercities = currentWeatherDBService.currentForecastByCity(city);
        return ResponseEntity.ok(weathercities);
    }

    @ApiOperation(value = "Obtener el pronostico actual por ciudad y pais")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operacion Exitosa", response = WeatherCityResponse.class, responseContainer = "WeatherCityResponse"),
            @ApiResponse(code = 404, message = "Ciudad o pais no encontrados"),
            @ApiResponse(code = 500, message = "Error interno del servidor"),
    })
    @GetMapping("/pronostico/city/country")
    public ResponseEntity<WeatherCityResponse> currentForecastByCityAndCountry(
            @ApiParam(value = "Nombre de la ciudad para la consulta", required = true)
            @RequestParam(name = "city") String city,
            @ApiParam(value = "Nombre del pais para la consulta", required = true)
            @RequestParam(name = "country") String country) {

        WeatherCityResponse weatherCity = currentWeatherDBService.currentForecastByCityAndCountry(city, country);
        return ResponseEntity.ok(weatherCity);
    }

}
