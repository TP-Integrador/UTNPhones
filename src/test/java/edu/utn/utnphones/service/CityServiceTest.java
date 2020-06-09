package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CityDao;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.exception.CityNotexistException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class CityServiceTest {

    private CityDao cityDao;
    private CityService cityService;

    @Before
    public void setUp(){
        cityDao = mock(CityDao.class);
        cityService = new CityService(cityDao);
    }

    @Test
    public void testGetByIdok() throws CityNotexistException {
        Optional<City> city = Optional.ofNullable(City.builder().cityId(1).build());
        when(cityDao.findById(1)).thenReturn(city);

        cityService.getById(1);
        verify(cityDao,times(1)).findById(1);

    }

    @Test(expected = CityNotexistException.class)
    public void testGetByIdNotExists() throws CityNotexistException {
        when(cityDao.findById(2)).thenReturn(null);
        cityService.getById(1);

    }
}
