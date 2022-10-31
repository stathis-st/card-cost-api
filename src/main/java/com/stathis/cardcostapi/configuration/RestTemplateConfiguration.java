package com.stathis.cardcostapi.configuration;

import com.stathis.cardcostapi.error.RestTemplateResponseErrorHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class RestTemplateConfiguration {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }
}
