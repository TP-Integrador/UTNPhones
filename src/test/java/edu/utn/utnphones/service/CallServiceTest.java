package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CallDao;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.projections.MostCalledCities;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallServiceTest {

    private CallDao callDao;
    private CallService callService;
    //private Call call;
    private GetCalls getCalls;
    private MostCalledCities mostCalledCities;

    @Before
    public void setUp(){
        callDao = mock(CallDao.class);
        //call = mock(Call.class);
        getCalls = mock(GetCalls.class);
        callService = new CallService(callDao);
    }

    @Test
    public void testGetAll(){
        List<Call> listCall = new ArrayList<>();
        when(callDao.findAll()).thenReturn(listCall);

        assertEquals(listCall,callService.getAll());
    }

    @Test
    public void testGetByIdOk() throws ResourcesNotExistException {
        Optional<Call> call = Optional.ofNullable(Call.builder().Id(1).build());
        when(callDao.findById(1)).thenReturn(call);

        assertEquals(call,Optional.of(callService.getById(1)));
    }

    @Test(expected = ResourcesNotExistException.class)
    public void testGetByIdNull() throws ResourcesNotExistException {
        when(callDao.findById(1)).thenReturn(Optional.ofNullable(null));
        callService.getById(1);
    }

    @Test
    public void testAddOk() throws SQLException {
        PhoneLine phoneLineFrom = PhoneLine.builder().id(123).build();
        PhoneLine phoneLineTo = PhoneLine.builder().id(456).build();
        Call call = Call.builder().Id(1).lineIdFrom(phoneLineFrom).lineIdTo(phoneLineTo).duration(60).date(new Date()).build();
        when(callDao.save(call)).thenReturn(call);
        callService.add("123","456",60,new Date());
    }

    @Test
    public void testGetCallsByDate() throws ParseException {
        List<Call> listCall = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(callDao.getCallsByDate(from,to,1)).thenReturn(listCall);

        assertEquals(listCall,callService.getCallsByDate(from,to,1));
    }

    @Test
    public void testGetCallsByClient(){
        List<GetCalls> listCall = new ArrayList<>();
        listCall.add(getCalls);
        when(callDao.getCallsByClient(1)).thenReturn(listCall);

        assertEquals(listCall,callService.getCallsByClient(1));
    }

    @Test(expected = SQLException.class)
    public void testAddSQLException() throws SQLException {
        doThrow(new SQLException()).doNothing();
        callService.add("123","456",60,new Date());
    }

    @Test
    public void getMostCalledCities(){
        List<MostCalledCities> list = new ArrayList<>();
        list.add(mostCalledCities);
        when(callDao.getMostCalledCities(1)).thenReturn(list);
        callService.getMostCalledCities(1);
    }
}
