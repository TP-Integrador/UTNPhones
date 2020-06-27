package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CityController;
import edu.utn.utnphones.controller.RateController;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.exception.CityNotexistException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckRateControllerTest {

    private RateController rateController;
    private CityController cityController;
    private CheckRateController checkRateController;


    @Before
    public void setUp() {
        rateController = mock(RateController.class);
        cityController = mock(CityController.class);
        checkRateController = new CheckRateController(rateController, cityController);
    }


    @Test
    public void testGetAllRatesOk() {
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);

        when(rateController.getAllRates()).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getAllRates();


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rateList, responseEntity.getBody());

    }

    @Test
    public void testGetAllRatesNoContent() {
        List<Rate> rateList = new ArrayList<>();
        rateList.clear();
        when(rateController.getAllRates()).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getAllRates();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testGetByIdFromOk() throws CityNotexistException {
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);

        when(cityController.getById(1)).thenReturn(from);
        when(rateController.getByIdFrom(1)).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getByIdFrom(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rateList, responseEntity.getBody());

    }

    @Test
    public void testGetByIdFromNotContent() throws CityNotexistException {
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.clear();

        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenReturn(to);
        when(rateController.getByIdFrom(1)).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getByIdFrom(1);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

    }

    @Test(expected = CityNotexistException.class)
    public void testGetByIdFromNotExists() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        when(cityController.getById(1)).thenThrow(new CityNotexistException());
        when(cityController.getById(2)).thenReturn(to);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getByIdFrom(1);
    }

    @Test
    public void testGetRateOk() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();

        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenReturn(to);
        when(rateController.getRate(1, 2)).thenReturn(rate);

        ResponseEntity<Rate> responseEntity = checkRateController.getRate(1, 2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rate, responseEntity.getBody());

    }

    @Test
    public void testGetRateContent() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();

        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenReturn(to);
        when(rateController.getRate(1, 2)).thenReturn(null);

        ResponseEntity<Rate> responseEntity = checkRateController.getRate(1, 2);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

    }

    @Test(expected = CityNotexistException.class)
    public void testGetRateFromNotExists() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        when(cityController.getById(1)).thenThrow(new CityNotexistException());
        when(cityController.getById(2)).thenReturn(to);

        ResponseEntity<Rate> responseEntity = checkRateController.getRate(1, 2);
    }

    @Test(expected = CityNotexistException.class)
    public void testGetRateToNotExists() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenThrow(new CityNotexistException());

        ResponseEntity<Rate> responseEntity = checkRateController.getRate(1, 2);
    }

}
