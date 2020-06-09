package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.CityDao;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.exception.CityNotexistException;
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

    //TODO validar si se usar
    /*
    public List<City> getAll(){
        return cityDao.findAll();
    }

    public City add(City city) {
        return cityDao.save(city);
    }
     */

    public City getById(int cityId) throws CityNotexistException {
        return cityDao.findById(cityId).orElseThrow(CityNotexistException::new);
    }
}
