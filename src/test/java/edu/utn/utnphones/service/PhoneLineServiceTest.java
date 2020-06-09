package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneLineServiceTest {

    private PhoneLineDao phoneLineDao;
    private PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        phoneLineDao = mock(PhoneLineDao.class);
        phoneLineService = new PhoneLineService(phoneLineDao);
    }
    
    @Test
    public void testGetByNumberOk() throws PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineDao.findByNumber("123")).thenReturn(phoneLine);

        phoneLineService.getByNumber("123");
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testGetByNumberNotExists() throws PhoneLineNotExistException {
        when(phoneLineDao.findByNumber("123")).thenReturn(null);

        phoneLineService.getByNumber("123");
    }
}
