package in.mpApp.JwtWithAWS.configurations;

import in.mpApp.JwtWithAWS.enums.RoleAuthority;
import in.mpApp.JwtWithAWS.filters.JWTAuthFilter;
import in.mpApp.JwtWithAWS.services.UserPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalService userPrincipalService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userPrincipalService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
//                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/registration").permitAll()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
                .antMatchers("/users").hasAuthority(RoleAuthority.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/books").hasAnyAuthority(RoleAuthority.ADMIN.name(), RoleAuthority.LIBRARIAN.name())
                .antMatchers(HttpMethod.PUT, "/books/**").hasAnyAuthority(RoleAuthority.ADMIN.name(), RoleAuthority.LIBRARIAN.name())
                .antMatchers(HttpMethod.DELETE, "/books/**").hasAuthority(RoleAuthority.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/books").hasAnyAuthority(RoleAuthority.ADMIN.name(), RoleAuthority.LIBRARIAN.name(), RoleAuthority.STUDENT.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
