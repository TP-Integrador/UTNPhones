package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.CityDao;
import edu.utn.utnphones.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    CityDao cityDao;

    @Autowired
    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public List<City> getAll(){
        return cityDao.findAll();
    }

    public void add(City city) {
        cityDao.save(city);
    }
}
