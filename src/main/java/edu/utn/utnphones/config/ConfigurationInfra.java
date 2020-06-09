package edu.utn.utnphones.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;


@org.springframework.context.annotation.Configuration
@PropertySource("infra.properties")
public  class ConfigurationInfra {


    @Value("${userconfig}")
    String userconfig;
    @Value("${passconfig}")
    String passconfig;
    private LoginInfra loginInfra;

    @Bean
    public LoginInfra getConfig(){
        loginInfra = new LoginInfra(this.userconfig,this.passconfig);
        return loginInfra;
    }

}
