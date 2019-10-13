package edu.baylor.flarn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityWebAppConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().antMatchers("**").permitAll();
    // http.authorizeRequests().antMatchers("/console/**").permitAll();

    http.csrf().disable();
    http.headers().frameOptions().disable();
  }
}