package edu.utn.utnphones.controller;


import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.dto.ClientDto;
import edu.utn.utnphones.exception.*;
import edu.utn.utnphones.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sun.nio.cs.US_ASCII;

import javax.jws.soap.SOAPBinding;
import java.awt.*;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class UserControllerTest {

    UserController userController;
    UserService service;


    @Before
    public void setUp() {
        service = mock(UserService.class);
        userController = new UserController(service);
    }

    @Test
    public void testLoginOk() throws UserNotexistException, ValidationException {
        User loggedUser = new User(1, "nme", "lastname", "12345678", "username", "password", true, City.builder().cityId(1).build(), UserType.builder().Id(1).build(),null);
        //Cuando llame al mock service.login devuelvo el logged user
        when(service.login("user", "pwd")).thenReturn(loggedUser);
        User returnedUser = userController.login("user", "pwd");

        //Hacemos los assert
        assertEquals(loggedUser.getUserId(), returnedUser.getUserId());
        assertEquals(loggedUser.getUsername(), returnedUser.getUsername());
        verify(service, times(1)).login("user", "pwd");
    }

    @Test(expected = UserNotexistException.class)
    public void testLoginUserNotFound() throws UserNotexistException, ValidationException {
        when(service.login("user", "pwd")).thenThrow(new UserNotexistException());
        userController.login("user", "pwd");
    }

    @Test(expected = ValidationException.class)
    public void testLoginInvalidData() throws UserNotexistException, ValidationException{
        when(service.login(null, "pwd")).thenThrow(new ValidationException("username and password must have a value"));
        userController.login(null, "pwd");
    }

    @Test
    public void testGetUserByIdOk() throws UserNotexistException {
        User user = new User(1, "name", "lastname", "12345678", "username", "password", true, City.builder().cityId(1).build(), UserType.builder().Id(1).build(),null);
        when(service.getUser(1)).thenReturn(user);
        userController.getUserById(1);
    }

    @Test(expected = UserNotexistException.class)
    public void testGetUserByIdNotExists() throws UserNotexistException {
        when(service.getUser(1)).thenThrow(new UserNotexistException());
        userController.getUserById(1);
    }

    @Test
    public void testGetClientByIdOK() throws ClientNotExistsException {
        User user = User.builder().userId(1).build();
        when(service.getClientById(1)).thenReturn(user);
        userController.getClientById(1);
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetClientByIdNotExists() throws ClientNotExistsException {
        when(service.getClientById(1)).thenThrow(new ClientNotExistsException());
        userController.getClientById(1);
    }

    @Test
    public void testaddClientOK() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(service.addClient(user)).thenReturn(user);
        userController.addClient(user);
    }

    @Test(expected = UserNameAlreadyExists.class)
    public void testaddClientUsernameAlreadyExists() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(service.addClient(user)).thenThrow(new UserNameAlreadyExists());
        userController.addClient(user);
    }

    @Test(expected = ClientDniAlreadyExists.class)
    public void testaddClienDNIAlreadyExists() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(service.addClient(user)).thenThrow(new ClientDniAlreadyExists());
        userController.addClient(user);
    }


    @Test
    public void testupdateClientOK() throws UserNameAlreadyExists, SQLException, ClientNotExistsException {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(service.updateClient(1,clientDto)).thenReturn(user);
        userController.updateClient(1,clientDto);
    }

    @Test(expected = UserNameAlreadyExists.class)
    public void testUpdateClientUsernameAlreadyExists() throws UserNameAlreadyExists, SQLException, ClientNotExistsException {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(service.updateClient(1,clientDto)).thenThrow(new UserNameAlreadyExists());
        userController.updateClient(1,clientDto);
    }

    @Test(expected = ClientNotExistsException.class)
    public void testaddClientNotExists() throws UserNameAlreadyExists, SQLException, ClientNotExistsException {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(service.updateClient(1,clientDto)).thenThrow(new ClientNotExistsException());
        userController.updateClient(1,clientDto);
    }

    /*
    public void deleteClient(Integer idClient) throws ClientNotExistsException, ClientRemovedException {
        userService.deleteClient(idClient);

     */

    @Test
    public void testDeleteClientOK() throws ClientNotExistsException, ClientRemovedException {
        Mockito.doNothing().when(service).deleteClient(1);
        userController.deleteClient(1);
    }

    @Test(expected = ClientNotExistsException.class)
    public void testdeleteClientUsernameAlreadyExists() throws ClientNotExistsException, ClientRemovedException {
        Mockito.doThrow(new ClientNotExistsException()).when(service).deleteClient(1);
        userController.deleteClient(1);
    }

    @Test(expected = ClientRemovedException.class)
    public void testdeleteClientRemoved() throws ClientNotExistsException, ClientRemovedException {
        Mockito.doThrow(new ClientRemovedException()).when(service).deleteClient(1);
        userController.deleteClient(1);
    }
}
