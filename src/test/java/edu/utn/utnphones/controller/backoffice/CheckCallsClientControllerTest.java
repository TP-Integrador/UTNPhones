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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckCallsClientControllerTest {

    private CheckCallsClientController checkCallsClientController;
    private CallController callController;
    private GetCalls getCalls;
    private Call call;

    @Before
    public void setUp(){
        callController = mock(CallController.class);
        getCalls = mock(GetCalls.class);
        call = mock(Call.class);
        checkCallsClientController = new CheckCallsClientController(callController);
    }

    @Test
    public void testGetCallsByClientDateOk() throws UserNotexistException, ParseException {
        List<GetCalls> listCall = new ArrayList<>();
        listCall.add(getCalls);
        when(callController.getCallsByClient(1)).thenReturn(listCall);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClientDate(null,null,1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(listCall,responseEntity.getBody());

    }

    @Test
    public void testGetCallsByClientNoContent() throws UserNotexistException, ParseException {
        List<GetCalls> listCall = Collections.emptyList();
        when(callController.getCallsByClient(1)).thenReturn(listCall);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClientDate(null,null,1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByDateOk() throws ParseException, UserNotexistException {
        List<GetCalls> callList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        callList.add(getCalls);
        when(callController.getCallsByDate(from,to,1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClientDate("2020-05-28","2020-06-30",1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(callList,responseEntity.getBody());

    }

    @Test
    public void testGetCallsByDateNoContent() throws ParseException, UserNotexistException {
        List<GetCalls> callList = Collections.emptyList();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(callController.getCallsByDate(from,to,1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClientDate("2020-05-28","2020-06-30",1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByDateNullFrom() throws ParseException, UserNotexistException {
        List<GetCalls> callList = new ArrayList<>();
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        callList.add(getCalls);
        when(callController.getCallsByDate(null,to,1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClientDate(null,"2020-06-30",1);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByDateTo() throws ParseException, UserNotexistException {
        List<GetCalls> callList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        callList.add(getCalls);
        when(callController.getCallsByDate(from,null,1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsClientController.getCallsByClientDate("2020-05-28",null,1);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }
}
