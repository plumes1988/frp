package com.figure.core.base;

import com.figure.core.filter.EncodeFilter;
import com.figure.core.helper.DateHelper;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

public class BaseApplication extends SpringBootServletInitializer {

    static{
        DateHelper.fixedChinaTimeZone();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    @Bean
    public FilterRegistrationBean encodeFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new EncodeFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("CustomFilter");
        registration.setOrder(1);
        registration.setEnabled(true);
        return registration;
    }

}
