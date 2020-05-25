package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Province;
import edu.utn.utnphones.exception.ResourceAlreadyExistsException;
import edu.utn.utnphones.service.CityService;
import edu.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class falopaController {
    ProvinceService provinceService;
    CityService cityService;

    @Autowired
    public falopaController(ProvinceService provinceService,CityService cityService) {
        this.provinceService = provinceService;
        this.cityService = cityService;
    }

    //MÉTODOS PROVINCE
    public List<Province> getAllProvinces(){
        return provinceService.getAll();
    }

    public void addProvince(Province p) throws ResourceAlreadyExistsException {
        provinceService.add(p);
    }

    //MÉTODOS CITY
    public List<City> getAllCities(){
        return cityService.getAll();
    }

    public void addCity(@RequestBody @Valid City city){
        cityService.add(city);
    }
}
