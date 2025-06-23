package com.sode.lsoauth.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class OAuthDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.oauth")
    DataSourceProperties oauthDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    DataSource oauthDataSource(){
        return oauthDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    JdbcTemplate JdbcTemplate(@Qualifier("oauthDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
