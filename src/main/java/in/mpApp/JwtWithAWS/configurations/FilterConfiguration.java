package in.mpApp.JwtWithAWS.configurations;

import in.mpApp.JwtWithAWS.filters.JWTAuthFilter;
import in.mpApp.JwtWithAWS.filters.LoggedInContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public JWTAuthFilter jwtAuthFilter() {
        return new JWTAuthFilter();
    }

    @Autowired
    @Bean(name = "jwtFilterRegistration")
    public FilterRegistrationBean<JWTAuthFilter> jwtAuthFilterRegistrationBean(final JWTAuthFilter jwtAuthFilter) {
        final FilterRegistrationBean<JWTAuthFilter> jwtAuthFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        jwtAuthFilterFilterRegistrationBean.setFilter(jwtAuthFilter);
        return jwtAuthFilterFilterRegistrationBean;
    }

    @Bean
    public LoggedInContextFilter loggedInContextFilter() {
        return new LoggedInContextFilter();
    }

    @Autowired
    @Bean(name = "loggedInContextFilterRegistration")
    public FilterRegistrationBean<LoggedInContextFilter> LoggedInContextFilterRegistrationBean(final LoggedInContextFilter filter) {
        final FilterRegistrationBean<LoggedInContextFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(filter);
        return filterRegistrationBean;
    }
}
