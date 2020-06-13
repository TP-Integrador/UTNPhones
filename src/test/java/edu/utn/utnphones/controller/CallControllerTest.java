package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.service.CallService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CallControllerTest {

    private CallService callService;
    private CallController callController;
    private Call call;
    private GetCalls getCalls;

    @Before
    public void setUp(){
        callService = mock(CallService.class);
        call = mock(Call.class);
        getCalls = mock(GetCalls.class);
        callController = new CallController(callService);
    }

    @Test
    public void testGetAllCalls(){
        List<Call> listCall = new ArrayList<>();
        when(callController.getAllCalls()).thenReturn(listCall);

        assertEquals(listCall,listCall);
    }

    @Test
    public void testGetCallsByIdOk() throws ResourcesNotExistException {
        when(callController.getById(1)).thenReturn(call);
        assertEquals(call,call);
    }



    @Test
    public void testAddCallOk() throws SQLException {
        callController.addCall("123","456",60,new Date());
    }

    @Test
    public void testGetCallsByDate() throws ParseException {
        List<Call> listCall = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(callService.getCallsByDate(from,to,1)).thenReturn(listCall);
        callController.getCallsByDate(from,to,1);
        verify(callService,times(1)).getCallsByDate(from,to,1);
    }

    @Test
    public void testGetCallsByClient(){
        List<GetCalls> listCall = new ArrayList<>();
        listCall.add(getCalls);
        when(callService.getCallsByClient(1)).thenReturn(listCall);
        callController.getCallsByClient(1);
        verify(callService,times(1)).getCallsByClient(1);
    }
}
