package edu.utn.utnphones.controller;

import edu.utn.utnphones.service.CallService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Mockito.*;

public class CallControllerTest {

    private CallService callService;
    private CallController callController;


    @Before
    public void setUp(){
        callService = mock(CallService.class);
        callController = new CallController(callService);
    }


    @Test
    public void testAddCallOk() throws SQLException {
        callController.addCall("123","456",60,new Date());
    }
}
