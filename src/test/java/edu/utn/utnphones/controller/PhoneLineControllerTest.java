package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.service.PhoneLineService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PhoneLineControllerTest {

    private PhoneLineService phoneLineService;
    private PhoneLineController phoneLineController;

    @Before
    public void setUp(){
        phoneLineService = mock(PhoneLineService.class);
        phoneLineController = new PhoneLineController(phoneLineService);
    }

    @Test
    public void testGetByNumberOk() throws PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineService.getByNumber("123")).thenReturn(phoneLine);

        phoneLineController.getByNumber("123");
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testGetByNumberNotExists() throws PhoneLineNotExistException {
        when(phoneLineService.getByNumber("123")).thenThrow(new PhoneLineNotExistException("123"));
        phoneLineController.getByNumber("123");
    }
}
