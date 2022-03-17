package com.exercise.sa2files.shared;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Setter
@ConfigurationProperties
public class Properties {

    private String directorio;

    @Bean(name="directorio")
    public String getDirectorio() { return this.directorio; }
}
