package com.figure.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfigurations {

    public class DateSource {

        @Value("${spring.datasource01.driver-class-name}")
        private String driverClassName;
        @Value("${spring.datasource01.jdbc-url}")
        private String url;
        @Value("${spring.datasource01.username}")
        private String username;
        @Value("${spring.datasource01.password}")
        private String password;

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Bean
    public DateSource dateSource(){
        return new DateSource();
    }
}
