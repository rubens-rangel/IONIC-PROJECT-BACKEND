package com.rubensrangel.BackendApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubensrangel.BackendApp.domain.PagamentoComBoleto;
import com.rubensrangel.BackendApp.domain.PagamentoComCartao;
import com.rubensrangel.BackendApp.services.DBService;
import com.rubensrangel.BackendApp.services.EmailService;
import com.rubensrangel.BackendApp.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
