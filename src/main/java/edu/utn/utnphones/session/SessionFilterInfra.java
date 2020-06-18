package edu.utn.utnphones.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
@PropertySource("infra.properties")
@Service
public class SessionFilterInfra extends OncePerRequestFilter {

    @Value("${userconfig}")
    String userconfig;
    @Value("${passconfig}")
    String passconfig;

    @Autowired
    private SessionManager sessionManager;

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
}