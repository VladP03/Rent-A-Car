package com.rentacar.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

        // Our recommendation is to use CSRF protection for any request that could be processed by a browser by normal users.
        // If you are only creating a service that is used by non-browser clients, you will likely want to disable CSRF protection.
        // Reasons:
        // 1. You are using another token mechanism.
        // 2. You want to simplify interactions between a client and the server.
        // Link: https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/csrf.html
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

