package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CallDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Mockito.*;

public class CallServiceTest {

    private CallDao callDao;
    private CallService callService;

    @Before
    public void setUp(){
        callDao = mock(CallDao.class);
        callService = new CallService(callDao);
    }

    public void add(String lineFrom, String lineTo, int seg , Date dateTime) throws SQLException {
        try {
            callDao.addCall(lineFrom, lineTo, seg, dateTime);
        }catch (Exception e){
            throw new SQLException();
        }
    }

    @Test
    public void testAddOk() throws SQLException {
        callService.add("123","456",60,new Date());
    }

}
