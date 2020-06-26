package edu.utn.utnphones.controller.client;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.projections.MostCalledCities;
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

public class CheckCallsControllerTest {


    private CheckCallsController checkCallsController;
    private CallController callController;
    private SessionManager sessionManager;
    private Call call;
    private GetCalls getCalls;
    private MostCalledCities mostCalledCities;


    @Before
    public void setUp() {
        callController = mock(CallController.class);
        sessionManager = mock(SessionManager.class);
        mostCalledCities = mock(MostCalledCities.class);
        call = mock(Call.class);
        getCalls = mock(GetCalls.class);
        checkCallsController = new CheckCallsController(callController,sessionManager);
    }

    @Test
    public void testGetCallsByDateOk() throws UserNotexistException, ParseException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Client").build());
        List<GetCalls> callList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        callList.add(getCalls);
        when(callController.getCallsByDate(from,to,1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsController.getCalls("token","2020-05-28","2020-06-30");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(callList,responseEntity.getBody());
    }

    @Test
    public void testGetCallsByDateNoContent() throws UserNotexistException, ParseException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Client").build());
        List<GetCalls> callList = Collections.emptyList();
        Date from = new Date();
        Date to = new Date(2) ;
        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        when(callController.getCallsByDate(from,to,1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsController.getCalls("token","2020-05-28","2020-06-30");

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());

    }

    @Test
    public void testGetCallsByDateNull() throws UserNotexistException, ParseException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Client").build());
        List<GetCalls> callList = new ArrayList<>();
        callList.add(getCalls);
        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        when(callController.getCallsByClient(1)).thenReturn(callList);

        ResponseEntity<List<GetCalls>> responseEntity = checkCallsController.getCalls("token",null,null);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(callList,responseEntity.getBody());
    }

    @Test
    public void getMostCalledCitiesOk() throws UserNotexistException {
        User user = User.builder().userId(1).build();
        when(sessionManager.getCurrentUser("token")).thenReturn(user);

        List<MostCalledCities> list = new ArrayList<>();
        list.add(mostCalledCities);

        when(callController.getMostCalledCities(user.getUserId())).thenReturn(list);

        ResponseEntity<List<MostCalledCities>> responseEntity = checkCallsController.getMostCalledCities("token");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(list,responseEntity.getBody());

    }

    @Test
    public void getMostCalledCitiesNoContent() throws UserNotexistException {
        User user = User.builder().userId(1).build();
        when(sessionManager.getCurrentUser("token")).thenReturn(user);

        List<MostCalledCities> list = new ArrayList<>();
        list.clear();

        when(callController.getMostCalledCities(user.getUserId())).thenReturn(list);

        ResponseEntity<List<MostCalledCities>> responseEntity = checkCallsController.getMostCalledCities("token");
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
