package cn.bankrupt.workflow.config;

import cn.bankrupt.workflow.filter.RequestBodyLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFilterConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean loggingFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RequestBodyLoggingFilter());
        return registrationBean;
    }
}
