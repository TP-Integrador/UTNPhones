package edu.utn.utnphones.session;

import edu.utn.utnphones.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class SessionManagerTest {

    Map<String, Session> sessionMap = new Hashtable<>();
    private Session session;
    private SessionManager sessionManager;

    @Before
    public void setUp(){
        sessionMap = mock(Map.class);
        session = mock(Session.class);
        sessionManager = new SessionManager();
    }

    @Test
    public void testCreateSessionOK(){
        User user = User.builder().userId(1).build();
        String token = "token";
        sessionMap.put(token, new Session(token,user, new Date()));
        sessionManager.createSession(user);
    }


    @Test
    public void testGetSessionOk(){
        User user = User.builder().userId(1).build();
        String token = "token";
        session = new Session(token,user,new Date());
        when(sessionMap.get(token)).thenReturn(session);
        session.setLastAction(new Date());
        sessionManager.getSession(token);

    }

    @Test
    public void testGetSessionTokenNull(){
        String token = null;
        sessionManager.getSession(token);

    }

    @Test
    public void testGetSessionNull(){
        User user = User.builder().userId(1).build();
        String token = "token";
        session = null;
        when(sessionMap.get(token)).thenReturn(session);
        sessionManager.getSession(token);

    }

    @Test
    public void testRemoveSessionOK(){
        String token = "token";
        sessionMap.remove(token);
        sessionManager.removeSession(token);
    }

}
