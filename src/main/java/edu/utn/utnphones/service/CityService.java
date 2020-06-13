package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.CityDao;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.exception.CityNotexistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    CityDao cityDao;

    @Autowired
    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }


    public City getById(int cityId) throws CityNotexistException {
        return cityDao.findById(cityId).orElseThrow(CityNotexistException::new);
    }
}
