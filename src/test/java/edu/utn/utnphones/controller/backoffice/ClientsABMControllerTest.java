package edu.utn.utnphones.controller.backoffice;

import com.mysql.cj.xdevapi.Client;
import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.controller.UserController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.ClientDto;
import edu.utn.utnphones.exception.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClientsABMControllerTest {

    private UserController userController;
    private ClientsABMController clientsABMController;

    @Before
    public void setUp() {
        // para mockear el getLocation() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        // }
        userController = mock(UserController.class);
        clientsABMController = new ClientsABMController(userController);
    }

    @After
    public void teardown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void testGetClientOK() throws ClientNotExistsException {
        User user = User.builder().userId(1).build();
        when(userController.getClientById(1)).thenReturn(user);
        ResponseEntity<User> responseEntity = clientsABMController.getClient(1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test(expected = ClientNotExistsException.class)
    public void testGetClientNotExist() throws ClientNotExistsException {
        when(userController.getClientById(1)).thenThrow(new ClientNotExistsException());
        ResponseEntity<User> responseEntity = clientsABMController.getClient(1);
    }


    @Test
    public void testAddClient() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(userController.addClient(user)).thenReturn(user);
        ResponseEntity<User> responseEntity = clientsABMController.addClient(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("http://localhost/1",responseEntity.getHeaders().getFirst("Location"));

    }

    @Test(expected = UserNameAlreadyExists.class)
    public void testAddClientUsernameAlreadyExists() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(userController.addClient(user)).thenThrow(new UserNameAlreadyExists());
        ResponseEntity<User> responseEntity = clientsABMController.addClient(user);

    }

    @Test(expected = ClientDniAlreadyExists.class)
    public void testAddClientDNIAlreadyExists() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(userController.addClient(user)).thenThrow(new ClientDniAlreadyExists());
        ResponseEntity<User> responseEntity = clientsABMController.addClient(user);

    }

    @Test(expected = SQLException.class)
    public void testAddClientSQLExcetion() throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        User user = User.builder().userId(1).build();
        when(userController.addClient(user)).thenThrow(new SQLException());
        ResponseEntity<User> responseEntity = clientsABMController.addClient(user);

    }

    @Test
    public void testUpdateClientOK() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(userController.updateClient(1,clientDto)).thenReturn(user);
        ResponseEntity<User> responseEntity = clientsABMController.updateClient(1,clientDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test(expected = ClientNotExistsException.class)
    public void testUpdateClientNotExist() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(userController.updateClient(1,clientDto)).thenThrow(new ClientNotExistsException());
        ResponseEntity<User> responseEntity = clientsABMController.updateClient(1,clientDto);

    }

    @Test(expected = SQLException.class)
    public void testUpdateClientSQLException() throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        User user = User.builder().userId(1).build();
        ClientDto clientDto = ClientDto.builder().name("name").build();
        when(userController.updateClient(1,clientDto)).thenThrow(new SQLException());
        ResponseEntity<User> responseEntity = clientsABMController.updateClient(1,clientDto);
    }

    /*
     @DeleteMapping("/{idClient}")
    public ResponseEntity<User> deleteClient(@PathVariable int idClient) throws ClientNotExistsException, ClientRemovedException {
        userController.deleteClient(idClient);
        return ResponseEntity.ok().build();
    }
     */

    @Test
    public void testDeleteClient() throws ClientNotExistsException, ClientRemovedException {
        Mockito.doNothing().when(userController).deleteClient(1);
        ResponseEntity<User> responseEntity = clientsABMController.deleteClient(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = ClientNotExistsException.class)
    public void testDeleteClientNotExists() throws ClientNotExistsException, ClientRemovedException {
        Mockito.doThrow(new ClientNotExistsException()).when(userController).deleteClient(1);
        ResponseEntity<User> responseEntity = clientsABMController.deleteClient(1);
    }

    @Test(expected = ClientRemovedException.class)
    public void testDeleteClientRemoved() throws ClientNotExistsException, ClientRemovedException {
        Mockito.doThrow(new ClientRemovedException()).when(userController).deleteClient(1);
        ResponseEntity<User> responseEntity = clientsABMController.deleteClient(1);
    }
}
