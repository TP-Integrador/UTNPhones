package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CheckCallsClientControllerTest {

    CheckCallsClientController checkCallsClientController;
    CallController callController;
    SessionManager sessionManager;
    GetCalls getCalls;
    Call call;

    @Before
    public void setUp(){
        callController = mock(CallController.class);
        sessionManager = mock(SessionManager.class);
        getCalls = mock(GetCalls.class);
        call = mock(Call.class);
        checkCallsClientController = new CheckCallsClientController(callController,sessionManager);
    }

    @Test
    public void testGetCallsByClientOk(){
        List<GetCalls> listCall = new ArrayList<>();
        listCall.add(getCalls);
        when(callController.getCallsByClient(1)).thenReturn(listCall);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClient("token",1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(listCall,responseEntity.getBody());

    }

    @Test
    public void testGetCallsByClientNoContent(){
        List<GetCalls> listCall = Collections.emptyList();
        when(callController.getCallsByClient(1)).thenReturn(listCall);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClient("token",1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByClientNull(){
        List<GetCalls> listCall = new ArrayList<>();
        listCall.add(getCalls);
        when(callController.getCallsByClient(1)).thenReturn(listCall);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClient("token",null);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByDateOk() throws ParseException, UserNotexistException {
        List<Call> callList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        callList.add(call);
        when(callController.getCallsByDate(from,to,1)).thenReturn(callList);

        ResponseEntity<List<Call>> responseEntity = checkCallsClientController.getCallsByDate("token","2020-05-28","2020-06-30",1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(callList,responseEntity.getBody());

    }

    @Test
    public void testGetCallsByDateNoContent() throws ParseException, UserNotexistException {
        List<Call> callList = Collections.emptyList();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(callController.getCallsByDate(from,to,1)).thenReturn(callList);

        ResponseEntity<List<Call>> responseEntity = checkCallsClientController.getCallsByDate("token","2020-05-28","2020-06-30",1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByDateNull() throws ParseException, UserNotexistException {
        List<Call> callList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        callList.add(call);
        when(callController.getCallsByDate(null,null,1)).thenReturn(callList);

        ResponseEntity<List<Call>> responseEntity = checkCallsClientController.getCallsByDate("token",null,null,1);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }
}
