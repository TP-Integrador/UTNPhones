package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.exception.CityNotexistException;
import edu.utn.utnphones.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    public City getById(int cityId) throws CityNotexistException {
        return cityService.getById(cityId);
    }
}
