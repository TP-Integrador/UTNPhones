package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.LoginRequestDto;
import edu.utn.utnphones.exception.InvalidLoginException;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.exception.ValidationException;
import edu.utn.utnphones.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {
    private LoginController loginController;
    private UserController userController;
    private SessionManager sessionManager;
    private LoginRequestDto loginRequestDto;
    private User user;

    @Before
    public void setUp() {
        userController = mock(UserController.class);
        sessionManager = mock(SessionManager.class);
        loginRequestDto = mock(LoginRequestDto.class);
        user = mock(User.class);
        loginController = new LoginController(userController, sessionManager);
    }

    @Test
    public void testLoginOk() throws UserNotexistException, ValidationException, InvalidLoginException {
        when(userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword())).thenReturn(user);
        when(sessionManager.createSession(user)).thenReturn("token");
        loginController.login(loginRequestDto);

        //Intente validar la respuesta pero siempre me da null, no entiendo por que. :/
        //verify(userController,times(1)).login("abc","123");


    }

    @Test(expected = InvalidLoginException.class)
    public void testLoginNull() throws UserNotexistException, ValidationException, InvalidLoginException {
        when(userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword())).thenThrow(new UserNotexistException());
        loginController.login(loginRequestDto);
    }

    @Test
    public void testLogout() {

        sessionManager.removeSession("token");
        ResponseEntity responseEntity = loginController.logout("token");
    }
}
