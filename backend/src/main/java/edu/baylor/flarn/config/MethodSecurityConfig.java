package edu.baylor.flarn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * {@link MethodSecurityConfig} class configures spring security for the method level authentication and authorization.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
// enables RolesAllowed
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}