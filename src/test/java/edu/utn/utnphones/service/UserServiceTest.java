package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.UserDao;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.dto.ClientDto;
import edu.utn.utnphones.exception.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class UserServiceTest {

    UserService service;

    @Mock
    UserDao dao;

    @Before
    public void setUp() {
            initMocks(this);
            service = new UserService(dao);
    }

    @Test
    public void testLoginOk() throws UserNotexistException, ValidationException {
        User loggedUser = new User(1, "nme", "lastname", "12345678", "username", "password", true, City.builder().cityId(1).build(), UserType.builder().Id(1).build(),null);
        when(dao.getByUsername("user","pwd")).thenReturn(loggedUser);
        User returnedUser = service.login("user","pwd");
        assertEquals(loggedUser.getUserId(), returnedUser.getUserId());
        assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
        verify(dao, times(1)).getByUsername("user","pwd");
    }

    @Test(expected = UserNotexistException.class)
    public void testLoginUserNotFound() throws UserNotexistException, ValidationException {
        when(dao.getByUsername("user","pwd")).thenReturn(null);
        service.login("user", "pwd");
    }

    @Test(expected = ValidationException.class)
    public void testLoginInvalidData() throws UserNotexistException, ValidationException {
        when(dao.getByUsername(null,"pwd")).thenReturn(null);
        service.login(null, "pwd");
    }

    @Test
    public void testGetUserOK() throws UserNotexistException {
        User user = new User(1, "nme", "lastname", "12345678", "username", "password", true, City.builder().cityId(1).build(), UserType.builder().Id(1).build(),null);
        when(dao.findById(1)).thenReturn(Optional.of(user));
        service.getUser(1);
    }

    @Test(expected = UserNotexistException.class)
    public void testGetUserNotExists() throws UserNotexistException {
        User user = null;
        when(dao.findById(1)).thenReturn(Optional.ofNullable(user));
        service.getUser(1);
    }


    @Test
    public void testGetClientById() throws ClientNotExistsException {
        User user = User.builder().userId(1).build();
        when(dao.findByIdAndUserType(1)).thenReturn(user);
        service.getClientById(1);

    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetClientByIdNotExists() throws ClientNotExistsException {
        User user = null;
        when(dao.findByIdAndUserType(1)).thenReturn(user);
        service.getClientById(1);
    }


    @Test
    public void testAddClientOK() throws SQLException, ClientDniAlreadyExists, UserNameAlreadyExists {
        User user = User.builder().userId(1).build();
        when(dao.save(user)).thenReturn(user);
        service.addClient(user);

    }

    @Test(expected = ClientDniAlreadyExists.class)
    public void testAddClientDNIAlreadyExists() throws SQLException, ClientDniAlreadyExists, UserNameAlreadyExists {
        User user = User.builder().DNI("123").build();
        when(dao.findByDni("123")).thenReturn(user);
        service.addClient(user);
    }

    @Test(expected = UserNameAlreadyExists.class)
    public void testAddClientUserNameAlreadyExists() throws SQLException, ClientDniAlreadyExists, UserNameAlreadyExists {
        User user = User.builder().username("name").build();
        when(dao.findByUserName("name")).thenReturn(user);
        service.addClient(user);
    }

    @Test(expected = SQLException.class)
    public void testAddClientSQLException() throws SQLException, ClientDniAlreadyExists, UserNameAlreadyExists {
        User user = User.builder().userId(1).build();
        Mockito.doNothing().doThrow(new SQLException());
        service.addClient(user);
    }

    @Test
    public void testUpdateClientOK() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();

        when(dao.findById(1)).thenReturn(Optional.of(user));
        when(dao.findByIdAndUserType(1)).thenReturn(null);
        when(dao.save(user)).thenReturn(any());
        service.updateClient(1,clientDto);

    }

    @Test(expected = ClientNotExistsException.class)
    public void testUpdateClientNotExists() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        User user = null;
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(dao.findById(1)).thenReturn(Optional.ofNullable(user));
        service.updateClient(1,clientDto);

    }

    @Test(expected = UserNameAlreadyExists.class)
    public void testUpdateClientUsernameAlreadyExists() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        User user = User.builder().userId(1).username("username").build();
        User user2 = User.builder().userId(2).username("username").build();
        ClientDto clientDto = ClientDto.builder().username("username").build();
        when(dao.findById(1)).thenReturn(Optional.ofNullable(user));
        when(dao.findByUserName("username")).thenReturn(user2);
        service.updateClient(1,clientDto);

    }

    @Test(expected = SQLException.class)
    public void testUpdateClientSQLException() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        ClientDto clientDto = ClientDto.builder().username("username").build();
        Mockito.doNothing().doThrow(new SQLException());
        service.updateClient(1,clientDto);

    }

    @Test
    public void testDeleteClientOK() throws ClientNotExistsException, ClientRemovedException {
        User user = User.builder().userId(1).active(true).build();
        when(dao.findById(1)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(dao).updateStatus(false,1);
        service.deleteClient(1);

    }

    @Test(expected = ClientNotExistsException.class)
    public void testDeleteClientNotExists() throws ClientNotExistsException, ClientRemovedException {
        User user = null;
        when(dao.findById(1)).thenReturn(Optional.ofNullable(user));
        Mockito.doNothing().when(dao).updateStatus(false,1);
        service.deleteClient(1);

    }

    @Test(expected = ClientRemovedException.class)
    public void testDeleteClientRemoved() throws ClientNotExistsException, ClientRemovedException {
        User user = User.builder().userId(1).active(false).build();
        when(dao.findById(1)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(dao).updateStatus(false,1);
        service.deleteClient(1);

    }
}
