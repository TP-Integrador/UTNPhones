package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.PhoneLineAlreadyExistsException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.PhoneLineRemovedException;
import edu.utn.utnphones.exception.StatusNotExistsException;
import edu.utn.utnphones.service.PhoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

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
    public void testGetByIdOk() throws PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineService.getById(1)).thenReturn(phoneLine);

        phoneLineController.getById(1);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testGetByIdNotExists() throws PhoneLineNotExistException {
        when(phoneLineService.getById(1)).thenThrow(new PhoneLineNotExistException(""));
        phoneLineController.getById(1);
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

    @Test
    public void testAddPhoneOk() throws PhoneLineAlreadyExistsException, SQLException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineService.addPhone(phoneLine)).thenReturn(phoneLine);
        phoneLineController.addPhone(phoneLine);

        verify(phoneLineService,times(1)).addPhone(phoneLine);
    }

    @Test(expected = PhoneLineAlreadyExistsException.class)
    public void testAddPhoneAlreadyExists() throws PhoneLineAlreadyExistsException, SQLException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineService.addPhone(phoneLine)).thenThrow(new PhoneLineAlreadyExistsException());
        phoneLineController.addPhone(phoneLine);
    }

    @Test(expected = SQLException.class)
    public void testAddPhoneSQLException() throws PhoneLineAlreadyExistsException, SQLException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineService.addPhone(phoneLine)).thenThrow(new SQLException());
        phoneLineController.addPhone(phoneLine);
    }

    @Test
    public void testUpdateStatusOk() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        Mockito.doNothing().when(phoneLineService).updateStatus(status,1);
        phoneLineController.updateStatus(status,1);

        verify(phoneLineService, times(1)).updateStatus(status,1);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testUpdateStatusNotExists() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        Mockito.doThrow(new PhoneLineNotExistException("")).when(phoneLineService).updateStatus(status,1);
        phoneLineController.updateStatus(status,1);

        verify(phoneLineService, times(1)).updateStatus(status,1);
    }

    @Test(expected = PhoneLineRemovedException.class)
    public void testUpdateStatusRemoved() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Active").build();
        PhoneLine phoneLine = PhoneLine.builder().lineStatus(PhoneLine.Status.Inactive).build();
        Mockito.doThrow(new PhoneLineRemovedException()).when(phoneLineService).updateStatus(status,1);
        phoneLineController.updateStatus(status,1);

        verify(phoneLineService, times(1)).updateStatus(status,1);
    }

    @Test(expected = StatusNotExistsException.class)
    public void testUpdateStatusNotExist() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Deleted").build();
        Mockito.doThrow(new StatusNotExistsException()).when(phoneLineService).updateStatus(status,1);
        phoneLineController.updateStatus(status,1);

        verify(phoneLineService, times(1)).updateStatus(status,1);
    }

    @Test
    public void testDeletePhoneOk() throws PhoneLineRemovedException, PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        Mockito.doNothing().when(phoneLineService).delete(1);
        phoneLineController.delete(1);

        verify(phoneLineService, times(1)).delete(1);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testDeletePhoneNotExists() throws PhoneLineRemovedException, PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        Mockito.doThrow(new PhoneLineNotExistException("")).when(phoneLineService).delete(1);
        phoneLineController.delete(1);

        verify(phoneLineService, times(1)).delete(1);
    }

    @Test(expected = PhoneLineRemovedException.class)
    public void testDeletePhoneRemoved() throws PhoneLineRemovedException, PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        Mockito.doThrow(new PhoneLineRemovedException()).when(phoneLineService).delete(1);
        phoneLineController.delete(1);

        verify(phoneLineService, times(1)).delete(1);
    }
}
