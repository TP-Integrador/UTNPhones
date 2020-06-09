package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CityController;
import edu.utn.utnphones.controller.RateController;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.exception.CityNotexistException;
import edu.utn.utnphones.session.SessionManager;
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
    private SessionManager sessionManager;
    private CityController cityController;
    private CheckRateController checkRateController;


    @Before
    public void setUp() {
        rateController = mock(RateController.class);
        sessionManager = mock(SessionManager.class);
        cityController = mock(CityController.class);
        checkRateController = new CheckRateController(sessionManager, rateController,cityController);
    }


    @Test
    public void testGetAllRatesOk(){
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);

        when(rateController.getAllRates()).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getAllRates("token");


        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(rateList,responseEntity.getBody());

    }

    @Test
    public void testGetAllRatesNoContent(){
        List<Rate> rateList = new ArrayList<>();
        rateList.clear();
        when(rateController.getAllRates()).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getAllRates("token");
        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    /*
    @GetMapping("/{idFrom}")
    public ResponseEntity<List<Rate>> getByIdFrom(@RequestHeader("Authorization") String sessionToken, @PathVariable int idFrom) throws CityNotexistException {
        ResponseEntity<List<Rate>> responseEntity = null;
        City from = cityController.getById(idFrom);
        List<Rate> rateList = rateController.getByIdFrom(idFrom);
        if (!rateList.isEmpty()) {
            responseEntity = ResponseEntity.ok().body(rateList);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }
    */

    @Test
    public void testGetByIdFromOk() throws CityNotexistException {
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);

        when(cityController.getById(1)).thenReturn(from);
        when(rateController.getByIdFrom(1)).thenReturn(rateList);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getByIdFrom("token",1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(rateList,responseEntity.getBody());

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

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getByIdFrom("token",1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test(expected = CityNotexistException.class)
    public void testGetByIdFromNotExists() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        when(cityController.getById(1)).thenThrow(new CityNotexistException());
        when(cityController.getById(2)).thenReturn(to);

        ResponseEntity<List<Rate>> responseEntity = checkRateController.getByIdFrom("token",1);
    }

    /*
    @GetMapping("/{idFrom}/{idTo}")
    public ResponseEntity<Rate> getRate(@RequestHeader("Authorization") String sessionToken, @PathVariable int idFrom, @PathVariable int idTo) throws UserNotexistException {
        ResponseEntity<Rate> responseEntity = null;
        Rate rate = rateController.getRate(idFrom, idTo);
        if ((rate != null)) {
            responseEntity = ResponseEntity.ok().body(rate);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }
    */

    @Test
    public void testGetRateOk() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();

        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenReturn(to);
        when(rateController.getRate(1,2)).thenReturn(rate);

        ResponseEntity<Rate> responseEntity = checkRateController.getRate("token",1,2);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(rate,responseEntity.getBody());

    }

    @Test
    public void testGetRateContent() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();

        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenReturn(to);
        when(rateController.getRate(1,2)).thenReturn(null);

        ResponseEntity<Rate> responseEntity = checkRateController.getRate("token",1,2);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test(expected = CityNotexistException.class)
    public void testGetRateFromNotExists() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        when(cityController.getById(1)).thenThrow(new CityNotexistException());
        when(cityController.getById(2)).thenReturn(to);

        ResponseEntity<Rate> responseEntity = checkRateController.getRate("token",1,2);
    }

    @Test(expected = CityNotexistException.class)
    public void testGetRateToNotExists() throws CityNotexistException {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        when(cityController.getById(1)).thenReturn(from);
        when(cityController.getById(2)).thenThrow(new CityNotexistException());

        ResponseEntity<Rate> responseEntity = checkRateController.getRate("token",1,2);
    }
}
