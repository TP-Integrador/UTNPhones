package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.exception.CityNotexistException;
import edu.utn.utnphones.service.CityService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CityControllerTest {

    private CityService cityService;
    private CityController cityController;

    @Before
    public void setUp(){
        cityService = mock(CityService.class);
        cityController = new CityController(cityService);
    }

    @Test()
    public void testGetByIdOk() throws CityNotexistException {
        City city = City.builder().cityId(1).build();
        when(cityService.getById(1)).thenReturn(city);

        cityController.getById(1);
        verify(cityService,times(1)).getById(1);
    }

    @Test(expected = CityNotexistException.class)
    public void testGetByIdException() throws CityNotexistException {
        when(cityService.getById(1)).thenThrow(new CityNotexistException());
        cityController.getById(1);
        verify(cityService,times(1)).getById(1);
    }
}
