package edu.baylor.flarn.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
  @Value("${jwt.secret_key}")
  private String secretKey;

  @Value("${jwt.validity_ms}")
  private long validity; // 1h
}
