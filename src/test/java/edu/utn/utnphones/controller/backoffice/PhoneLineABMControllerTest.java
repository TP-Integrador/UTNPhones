package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PhoneLineABMControllerTest {

    private PhoneLineController phoneLineController;
    private PhoneLinesABMController phoneLinesABMController;

    @Before
    public void setUp() {
        // para mockear el getLocation() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        // }
        phoneLineController = mock(PhoneLineController.class);
        phoneLinesABMController = new PhoneLinesABMController(phoneLineController);
    }

    @After
    public void teardown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void testGetPhoneLineByIdOK() throws PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineController.getById(1)).thenReturn(phoneLine);
        ResponseEntity<PhoneLine> responseEntity = phoneLinesABMController.getPhonelineById(1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(phoneLine, responseEntity.getBody());

    }


    @Test(expected = PhoneLineNotExistException.class)
    public void testGetPhoneLineByIdNotExists() throws PhoneLineNotExistException {
        when(phoneLineController.getById(1)).thenThrow(new PhoneLineNotExistException(""));
        ResponseEntity<PhoneLine> responseEntity = phoneLinesABMController.getPhonelineById(1);
    }


    @Test
    public void testAddPhoneLineOK() throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineController.addPhone(phoneLine)).thenReturn(phoneLine);
        ResponseEntity responseEntity = phoneLinesABMController.addPhoneLine(phoneLine);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("http://localhost/1",responseEntity.getHeaders().getFirst("Location"));
    }

    @Test(expected = PhoneLineAlreadyExistsException.class)
    public void testAddPhoneLineAlreadyExist() throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineController.addPhone(phoneLine)).thenThrow(new PhoneLineAlreadyExistsException());
        ResponseEntity responseEntity = phoneLinesABMController.addPhoneLine(phoneLine);

        verify(phoneLineController, times(1)).addPhone(phoneLine);
    }


    @Test
    public void testUpdatePhoneLineOK() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        Mockito.doNothing().when(phoneLineController).updateStatus(status,1);
        ResponseEntity responseEntity = phoneLinesABMController.UpdatePhoneline(1,status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testUpdatePhoneLineNotExists() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        Mockito.doThrow(new PhoneLineNotExistException("")).when(phoneLineController).updateStatus(status,1);
        phoneLinesABMController.UpdatePhoneline(1,status);
    }

    @Test(expected = PhoneLineRemovedException.class)
    public void testUpdatePhoneLineRemoved() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Active").build();
        Mockito.doThrow(new PhoneLineRemovedException()).when(phoneLineController).updateStatus(status,1);
        phoneLinesABMController.UpdatePhoneline(1,status);
    }

    @Test(expected = PhoneLineRemovedException.class)
    public void testUpdatePhoneLineStatusNotExist() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        StatusPhoneDto status = StatusPhoneDto.builder().status("Removed").build();
        Mockito.doThrow(new PhoneLineRemovedException()).when(phoneLineController).updateStatus(status,1);
        phoneLinesABMController.UpdatePhoneline(1,status);
    }

    @Test
    public void testDeletePhoneLineOK() throws PhoneLineNotExistException, PhoneLineRemovedException {
        Mockito.doNothing().when(phoneLineController).delete(1);
        ResponseEntity responseEntity = phoneLinesABMController.deletePhoneLine(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testDeletePhoneLineNotExists() throws PhoneLineNotExistException, PhoneLineRemovedException {
        Mockito.doThrow(new PhoneLineNotExistException("")).when(phoneLineController).delete(1);
        phoneLinesABMController.deletePhoneLine(1);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testDeletePhoneLineRemoved() throws PhoneLineNotExistException, PhoneLineRemovedException {
        Mockito.doThrow(new PhoneLineNotExistException("")).when(phoneLineController).delete(1);
        phoneLinesABMController.deletePhoneLine(1);
    }

}
