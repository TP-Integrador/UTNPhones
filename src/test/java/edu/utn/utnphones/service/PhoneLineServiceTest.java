package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.dao.UserDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneLineServiceTest {

    private PhoneLineDao phoneLineDao;
    private PhoneLineService phoneLineService;
    private UserDao userDao;


    @Before
    public void setUp() {
        phoneLineDao = mock(PhoneLineDao.class);
        userDao = mock(UserDao.class);
        phoneLineService = new PhoneLineService(phoneLineDao, userDao);
    }

    @Test
    public void testGetByIdOk() throws PhoneLineNotExistException {
        Optional phoneLine = Optional.ofNullable(PhoneLine.builder().id(1).build());
        when(phoneLineDao.findById(1)).thenReturn(phoneLine);
        Assert.assertEquals(phoneLine, Optional.of(phoneLineService.getById(1)));
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testGetByIdNotExists() throws PhoneLineNotExistException {
        PhoneLine phoneLine = null;
        when(phoneLineDao.findById(1)).thenReturn(Optional.ofNullable(phoneLine));
        phoneLineService.getById(1);
    }

    @Test
    public void testGetByNumberOk() throws PhoneLineNotExistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).build();
        when(phoneLineDao.findByNumber("123")).thenReturn(phoneLine);

        phoneLineService.getByNumber("123");
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testGetByNumberNotExists() throws PhoneLineNotExistException {
        PhoneLine phoneLine = null;
        when(phoneLineDao.findByNumber("123")).thenReturn(phoneLine);
        phoneLineService.getByNumber("123");
    }

    @Test
    public void testAddPhoneOk() throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).user(User.builder().userId(1).build()).build();
        when(phoneLineDao.findByNumber("123")).thenReturn(null);
        User user = User.builder().userId(phoneLine.getUser().getUserId()).userType(UserType.builder().type("Client").build()).build();
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(phoneLineDao.save(phoneLine)).thenReturn(phoneLine);
        phoneLineService.addPhone(phoneLine);
    }

    @Test(expected = PhoneLineAlreadyExistsException.class)
    public void testAddPhoneAlreadyExists() throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine phoneLine = PhoneLine.builder().lineNumber("123").build();
        when(phoneLineDao.findByNumber("123")).thenReturn(phoneLine);
        phoneLineService.addPhone(phoneLine);
    }

    @Test(expected = UserNotexistException.class)
    public void testAddPhoneUserNotExist() throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).user(User.builder().userId(1).build()).build();
        when(phoneLineDao.findByNumber("123")).thenReturn(null);
        User user = User.builder().userId(phoneLine.getUser().getUserId()).userType(UserType.builder().type("Employee").build()).build();
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        phoneLineService.addPhone(phoneLine);
    }

    @Test
    public void testUpdateStatusOk() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).lineStatus(PhoneLine.Status.Active).build();
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        when(phoneLineDao.findById(1)).thenReturn(Optional.of(phoneLine));
        Mockito.doNothing().when(phoneLineDao).updateStatus("Suspended", 1);
        phoneLineService.updateStatus(status, 1);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testUpdateStatusNotExists() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        PhoneLine phoneLine = null;
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        when(phoneLineDao.findById(1)).thenReturn(Optional.ofNullable(phoneLine));
        phoneLineService.updateStatus(status, 1);
    }

    @Test(expected = PhoneLineRemovedException.class)
    public void testUpdateStatusRemoved() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).lineStatus(PhoneLine.Status.Inactive).build();
        StatusPhoneDto status = StatusPhoneDto.builder().status("Suspended").build();
        when(phoneLineDao.findById(1)).thenReturn(Optional.of(phoneLine));
        phoneLineService.updateStatus(status, 1);
    }

    @Test(expected = StatusNotExistsException.class)
    public void testUpdateStatusNotExistsEnum() throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).lineStatus(PhoneLine.Status.Active).build();
        StatusPhoneDto status = StatusPhoneDto.builder().status("sarasa").build();
        when(phoneLineDao.findById(1)).thenReturn(Optional.of(phoneLine));
        phoneLineService.updateStatus(status, 1);
    }

    @Test
    public void testdeleteOk() throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).lineStatus(PhoneLine.Status.Active).build();
        when(phoneLineDao.findById(1)).thenReturn(Optional.of(phoneLine));
        Mockito.doNothing().when(phoneLineDao).delete(phoneLine);
        phoneLineService.delete(1);
    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testDeleteNotExists() throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine phoneLine = null;
        when(phoneLineDao.findById(1)).thenReturn(Optional.ofNullable(phoneLine));
        phoneLineService.delete(1);
    }

    @Test(expected = PhoneLineRemovedException.class)
    public void testDeleteRemoved() throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine phoneLine = PhoneLine.builder().id(1).lineStatus(PhoneLine.Status.Inactive).build();
        when(phoneLineDao.findById(1)).thenReturn(Optional.of(phoneLine));
        phoneLineService.delete(1);
    }

}
