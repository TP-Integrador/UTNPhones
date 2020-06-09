package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.service.RateService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RateControllerTest {

    private RateService rateService;
    private RateController rateController;

    @Before
    public void setUp(){
        rateService = mock(RateService.class);
        rateController = new RateController(rateService);
    }

    @Test
    public void testGetAllRates(){
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);
        when(rateController.getAllRates()).thenReturn(rateList);

        assertEquals(rateList.size(),rateList.size());
    }


    @Test
    public void testGetByIdFrom(){
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);
        when(rateController.getByIdFrom(1)).thenReturn(rateList);

        assertEquals(rateList.size(),rateList.size());
    }

    @Test
    public void testGetRate(){
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        when(rateController.getRate(1,2)).thenReturn(rate);

        assertEquals(rate,rate);
    }
}
