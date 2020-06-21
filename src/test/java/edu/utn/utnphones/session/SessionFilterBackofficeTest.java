package edu.utn.utnphones.session;

import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.*;

public class SessionFilterBackofficeTest {

    private SessionManager sessionManager;
    private SessionFilterBackoffice sessionFilterBackoffice;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @Before
    public void setUp(){
        sessionManager = mock(SessionManager.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        sessionFilterBackoffice = new SessionFilterBackoffice(sessionManager);
    }


    @Test
    public void testDoFilterInternalOK() throws IOException, ServletException {
        String sessionToken = "token";
        User user = User.builder().userId(1).userType(UserType.builder().type("Employee").build()).build();
        when(request.getHeader("Authorization")).thenReturn(sessionToken);
        Session session = new Session("token",user, new Date());
        when(sessionManager.getSession("token")).thenReturn(session);

        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        doNothing().when(filterChain).doFilter(request,response);

        sessionFilterBackoffice.doFilterInternal(request,response,filterChain);

    }

    @Test
    public void testDoFilterInternalNotEmployee() throws IOException, ServletException {
        String sessionToken = "token";
        when(request.getHeader("Authorization")).thenReturn(sessionToken);
        User user = User.builder().userId(1).userType(UserType.builder().type("Client").build()).build();
        Session session = new Session("token",user, new Date());
        when(sessionManager.getSession("token")).thenReturn(session);
        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        Mockito.doNothing().when(response).setStatus(HttpStatus.FORBIDDEN.value());

        sessionFilterBackoffice.doFilterInternal(request,response,filterChain);

    }

    @Test
    public void testDoFilterInternalSessionNull() throws IOException, ServletException {
        String sessionToken = "token";
        when(request.getHeader("Authorization")).thenReturn(sessionToken);
        User user = User.builder().userId(1).userType(UserType.builder().type("Client").build()).build();
        Session session = new Session("token",user, new Date());
        when(sessionManager.getSession("token")).thenReturn(null);

        sessionFilterBackoffice.doFilterInternal(request,response,filterChain);

    }
}
