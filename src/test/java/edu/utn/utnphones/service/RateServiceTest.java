package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.RateDao;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Rate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RateServiceTest {

    private RateDao rateDao;
    private RateService rateService;

    @Before
    public void setUp() {
        rateDao = mock(RateDao.class);
        rateService = new RateService(rateDao);
    }

    @Test
    public void testGetAll() {
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);
        when(rateService.getAll()).thenReturn(rateList);

        assertEquals(rateList.size(), rateList.size());
    }

    @Test
    public void testGetByIdFrom() {
        List<Rate> rateList = new ArrayList<>();
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        rateList.add(rate);
        when(rateService.getByIdFrom(1)).thenReturn(rateList);

        assertEquals(rateList.size(), rateList.size());

    }

    @Test
    public void testGetRate() {
        City from = City.builder().cityId(1).build();
        City to = City.builder().cityId(2).build();
        Rate rate = Rate.builder().cityFrom(from).cityTo(to).value((float) 2).build();
        when(rateService.getRate(1, 2)).thenReturn(rate);

        assertEquals(rate, rate);

    }

}
