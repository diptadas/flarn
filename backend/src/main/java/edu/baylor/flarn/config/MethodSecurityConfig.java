package edu.baylor.flarn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/***
 * Spring Configuration file.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
// enables RolesAllowed
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}