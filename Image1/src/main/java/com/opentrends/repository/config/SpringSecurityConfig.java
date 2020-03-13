package com.opentrends.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Class for configure security.
 * I use a auth in memory (an admin). I have secured the controller but everyone can access to H2-Console for demo purposes.
 * @author iorodriguez
 *
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *  Create admin user for demo
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}password").roles("ADMIN");

    }

    /** 
     * Secure the endpoints with HTTP Basic authentication
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/repository").hasRole("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

}
