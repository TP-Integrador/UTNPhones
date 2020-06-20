package edu.utn.utnphones.config;

import edu.utn.utnphones.session.SessionFilterBackoffice;
import edu.utn.utnphones.session.SessionFilterClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/*
public class ConfigurationTest {
    Configuration configuration;
    SessionFilterClient sessionFilterClient;
    SessionFilterBackoffice sessionFilterBackOffice;
    FilterRegistrationBean filterRegistrationBean;

    @Before
    public void setUp(){
        sessionFilterClient = mock(SessionFilterClient.class);
        filterRegistrationBean = mock(FilterRegistrationBean.class);
    }

    @Test
    public void testFilterClient(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilterClient);
        when(configuration.sessionFilterClient).thenReturn(registration);
    }
}

 */
