package edu.utn.utnphones.session;

import edu.utn.utnphones.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service

public class SessionFilterClient extends OncePerRequestFilter {


    private SessionManager sessionManager;

    @Autowired
    public SessionFilterClient(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");
        Session session = sessionManager.getSession(sessionToken);
        if (null != session) {
            User u = sessionManager.getCurrentUser(sessionToken);
            if (u.getUserType().getType().matches("Client")) {
                filterChain.doFilter(request, response);
            }else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}