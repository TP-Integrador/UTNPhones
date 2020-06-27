package edu.utn.utnphones.session;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class SessionFilterInfraTest {

    private SessionManager sessionManager;
    private SessionFilterInfra sessionFilterInfra;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;


    @Before
    public void setUp() {
        sessionManager = mock(SessionManager.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        sessionFilterInfra = new SessionFilterInfra();
    }
/*
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String user = request.getHeader("User");
        String pass = request.getHeader("Pass");
        if (user.matches(userconfig) && pass.matches(passconfig)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }

 */

    @Test
    public void testDoFilterInternalOK() throws IOException, ServletException {
        String user = "infra";
        String pass = "1234";

        when(request.getHeader("User")).thenReturn(user);
        when(request.getHeader("Pass")).thenReturn(pass);
        sessionFilterInfra.setUserconfig("infra");
        sessionFilterInfra.setPassconfig("1234");
        doNothing().when(filterChain).doFilter(request, response);

        sessionFilterInfra.doFilterInternal(request, response, filterChain);

    }

    @Test
    public void testDoFilterInternalForbidden() throws IOException, ServletException {
        String user = "aaaa";
        String pass = "4444";

        when(request.getHeader("User")).thenReturn(user);
        when(request.getHeader("Pass")).thenReturn(pass);
        sessionFilterInfra.setUserconfig("infra");
        sessionFilterInfra.setPassconfig("1234");
        doNothing().when(filterChain).doFilter(request, response);

        sessionFilterInfra.doFilterInternal(request, response, filterChain);

    }
}
