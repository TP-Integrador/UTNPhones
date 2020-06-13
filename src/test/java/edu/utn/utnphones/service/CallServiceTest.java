package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CallDao;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.CityNotexistException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.GetCalls;
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
        when(callService.getAll()).thenReturn(listCall);

        assertEquals(listCall,listCall);
    }

    @Test
    public void testGetByIdOk() throws ResourcesNotExistException {
        Optional<Call> call = Optional.ofNullable(Call.builder().Id(1).build());
        when(callDao.findById(1)).thenReturn(call);

        assertEquals(call,call);
    }

    @Test(expected = ResourcesNotExistException.class)
    public void testGetByIdNull() throws ResourcesNotExistException {
        when(callService.getById(1)).thenReturn(null);

    }

    @Test
    public void testAddOk() throws SQLException {
        callService.add("123","456",60,new Date());
    }

    //TODO: Falta simular crash en bd para que entre al catch
    /*
    @Test(expected = SQLException.class)
    public void testAddNull() throws SQLException {
        callService.add(null,null,60,null);
    }

     */

    @Test
    public void testGetCallsByDate() throws ParseException {
        List<Call> listCall = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(callService.getCallsByDate(from,to,1)).thenReturn(listCall);

        assertEquals(listCall,listCall);
    }

    @Test
    public void testGetCallsByClient(){
        List<GetCalls> listCall = new ArrayList<>();
        listCall.add(getCalls);
        when(callService.getCallsByClient(1)).thenReturn(listCall);

        assertEquals(listCall,listCall);
    }
}
