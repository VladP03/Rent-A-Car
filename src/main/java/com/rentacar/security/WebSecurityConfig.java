package com.rentacar.security;

import com.rentacar.service.exceptions.ApiError;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurityConfig(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                // Car
                .antMatchers(HttpMethod.GET,"/v1/cars").authenticated()     // All cars
                .antMatchers("/v1/cars").hasRole("ADMIN")
                // City
                .antMatchers(HttpMethod.GET,"/v1/city").authenticated()     // All cities
                .antMatchers("/v1/city").hasRole("ADMIN")
                // Country
                .antMatchers(HttpMethod.GET,"/v1/country").authenticated()     // All countries
                .antMatchers("/v1/country").hasRole("ADMIN")
                // Dealership
                .antMatchers(HttpMethod.GET,"/v1/dealership").authenticated()     // All dealerships
                .antMatchers("/v1/country").hasRole("ADMIN")
                // Rent
                .antMatchers("/v1/rent").hasRole("ADMIN")     // All rents
                // User
                .antMatchers(HttpMethod.POST,"/v1/user").permitAll()        // Create new user
                .antMatchers(HttpMethod.GET,"/v1/user").hasRole("ADMIN")    // Get all users

                .and()
                .httpBasic();
    }
}

