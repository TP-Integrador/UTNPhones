package edu.utn.utnphones.config;


import edu.utn.utnphones.session.SessionFilterBackoffice;
import edu.utn.utnphones.session.SessionFilterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@org.springframework.context.annotation.Configuration
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilterClient sessionFilterClient;

    @Autowired
    SessionFilterBackoffice sessionFilterBackOffice;


    @Bean
    public FilterRegistrationBean FilterClient() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilterClient);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean FilterBackoffice() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilterBackOffice);
        registration.addUrlPatterns("/backoffice/*");
        return registration;
    }


}
