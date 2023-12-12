package com.vates.provincia.seguros.techchallenge.client;

import com.vates.provincia.seguros.techchallenge.dto.Country;
import com.vates.provincia.seguros.techchallenge.dto.Location;
import com.vates.provincia.seguros.techchallenge.dto.Region;
import com.vates.provincia.seguros.techchallenge.dto.WeatherCurrentConditions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "accuweather", url = "${accuweather.base.uri}")
public interface AccuWeatherClient {

    @GetMapping("/locations/v1/regions?apikey={apikey}&language={language}")
    List<Region> getAllRegions(@RequestParam(name = "apikey") String apiKey,
                               @RequestParam(name = "language") String language);

    @GetMapping("/locations/v1/countries/{idRegion}?apikey={apikey}&language={language}")
    List<Country> getCountriesByCodeRegion(@RequestParam(name = "idRegion") String codeRegion,
                                           @RequestParam(name = "apikey") String apikey,
                                           @RequestParam(name="language") String language);

    @GetMapping("/currentconditions/v1/{idLocation}?apikey={apikey}&language={language}&details={detail}")
    List<WeatherCurrentConditions> getCurrentConditionsByLocation(@RequestParam(name = "idLocation") String idLocation,
                                                                  @RequestParam(name = "apikey") String apikey,
                                                                  @RequestParam(name = "language") String language,
                                                                  @RequestParam(name = "detail") boolean detail);

    @GetMapping("/locations/v1/cities/autocomplete?apikey={apikey}&q={q}&language={language}")
    List<Location> getIdLocationsByText(@RequestParam(name = "apikey") String apiKey,
                                        @RequestParam(name = "q") String text,
                                        @RequestParam(name = "language") String language);
}
