package edu.utn.utnphones.session;

import edu.utn.utnphones.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SessionTest {
    private String token;
    private User user;
    private Date date;
    private Session session;


    @Before
    public void setUp(){
        user = mock(User.class);
        session = new Session(token,user,date);
    }

    @Test
    public void testGetToken(){
        session.setToken("token");
        String token = session.getToken();
        assertEquals("token",token);
    }

    @Test
    public void testSetToken(){
        session.setToken("token");
        assertEquals("token",session.getToken());
    }

    @Test
    public void testGetLoggedUser(){
        session.setLoggedUser(user);
        User userLogged = session.getLoggedUser();
        assertEquals(userLogged,session.getLoggedUser());
    }

    @Test
    public void testSetLoggedUser(){
        session.setLoggedUser(user);
        assertEquals(user,session.getLoggedUser());
    }

    @Test
    public void testGetLastAction(){
        session.setLastAction(date);
        Date dateLast = session.getLastAction();
        assertEquals(dateLast,session.getLastAction());
    }

    @Test
    public void testSetLastAction(){
        session.setLastAction(date);
        assertEquals(date,session.getLastAction());
    }

}
